package at.eg.sprfrm.cmrdqi.services;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiExecutionStatusType;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.model.TestDqiFactoryObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
public class TestSimpleDqiDefinitionExecutor {
	
	
	private static final Logger log=LoggerFactory.getLogger(TestSimpleDqiDefinitionExecutor.class);

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiDefinitionDao")
	private IDqiDefinitionDao defintionDao;
	
	
	@Autowired
	@Qualifier("dqiExecutionDao")
	private IDqiExecutionDao executionDao;
	
	@Autowired
	private TestDqiFactoryObject factoryObject;
	
	@Autowired
	private IDqiDefinitionExecutor executorService;
	
	
	private void checkStartAndEndTimesCompletion(Long executionId) {
		log.info("Checking if startTime and endTime are filled in for the Execution Database Record");
		Integer c=jdbcT.queryForObject("SELECT count(*) from cmt_check_execution where (ex_start is null "
				+ "or ex_end is null or nvl(ex_start,trunc(sysdate))>nvl(ex_end,trunc(sysdate))) and  ex_id=?",Integer.class,executionId);
		assertEquals("Start Execution Time and End Execution Time should always be populated",(Integer)0,c);
	}
	
	@After
	public void cleanupDatabase() {
		log.info("After method called");
		log.info("Rows deleted1:"+jdbcT.update("DELETE FROM CMT_CHECK_EXECUTION WHERE LAST_USER=?",factoryObject.getDefaultLastUser()));
		//just to be sure, although it should propagate through the foreign keys
		log.info("Rows deleted2:"+jdbcT.update("DELETE FROM CMT_CHECK_ISSUE WHERE LAST_USER=?",factoryObject.getDefaultLastUser()));
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void runDqiDefinitionWithError() throws InterruptedException, ExecutionException, TimeoutException {
		
		log.info("Starting test...");
		DqiRequest request=factoryObject.getAsReferenceTestRequest();
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		
		log.info("Submited the task...");
		Future<DqiExecution> fExec=executorService.runDqi(request, definition);
				
		log.info("Get the result...");
		DqiExecution execution=fExec.get(3, TimeUnit.MINUTES);
		
		log.info("Check the state of the database...");
		
		
		String sql="select EX_ID,ISS_ID,EX_STATUS from cmt_check_execution ex inner join cmt_check_issue iss "
				+ "on ex.ex_id=iss.iss_ex_id and ex.last_user=iss.last_user and ex.last_user=?";
		
		
		List<Map<String,Object>> list=jdbcT.queryForList(sql,factoryObject.getDefaultLastUser());
		
		
		assertEquals("There should be 1 and only 1 Exection record in the table. Please check what was generated for this test and also have a look at the filter from query."
				,1,list.size());
		
		
		assertEquals("The id form the database should be equal with the same from the DqiExecution Java object",execution.getId().toString(),list.get(0).get("EX_ID").toString());
		
		assertEquals("The id form the database should be equal with the same from the DqiIssue Java object",execution.getIssueList().get(0).getId().toString(),list.get(0).get("ISS_ID").toString());
		
		assertEquals("The status of the Execution Java Object should be ["+DqiExecutionStatusType.ERROR.value()+"]",DqiExecutionStatusType.ERROR.value(),execution.getStatus());
		
		assertEquals("The status of the Execution Database Record should be ["+DqiExecutionStatusType.ERROR.value()+"]",DqiExecutionStatusType.ERROR.value(),list.get(0).get("EX_STATUS").toString());
		
		checkStartAndEndTimesCompletion(execution.getId());
		
	}
	
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void runDqiDefinitionWithSuccess() throws InterruptedException, ExecutionException, TimeoutException {
		
		log.info("Starting test ...");
		DqiRequest request=factoryObject.getAsReferenceTestRequest();
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(1);
		
		log.info("Submited the task...");
		Future<DqiExecution> fExec=executorService.runDqi(request, definition);
		
		
		log.info("Get the result...");
		DqiExecution execution=fExec.get(3, TimeUnit.MINUTES);
		
		log.info("Check the state of the database...");
		
		
		String sql="select EX_ID,EX_STATUS,ISS_ID from cmt_check_execution ex left join cmt_check_issue iss "
				+ "on ex.ex_id=iss.iss_ex_id where ex.last_user=?";
		
		
		List<Map<String,Object>> list=jdbcT.queryForList(sql,factoryObject.getDefaultLastUser());
		
		
		assertEquals("There should be 1 and only 1 Exection record in the table. Please check what was generated for this test and also have a look at the filter from query."
				,1,list.size());
		
		
		assertEquals("The id form the database should be equal with the same from the DqiExecution Java object",execution.getId().toString(),list.get(0).get("EX_ID").toString());
		
		assertNull("The id form the database should be null for issue",list.get(0).get("ISS_ID"));
		
		assertEquals("The status of the Execution Java Object should be ["+DqiExecutionStatusType.SUCCESS.value()+"]",DqiExecutionStatusType.SUCCESS.value(),execution.getStatus());
		
		assertEquals("The status of the Execution Database Record should be ["+DqiExecutionStatusType.SUCCESS.value()+"]",DqiExecutionStatusType.SUCCESS.value(),list.get(0).get("EX_STATUS").toString());
		
		checkStartAndEndTimesCompletion(execution.getId());
				
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void runDqiDefinitionWithException() throws InterruptedException, ExecutionException, TimeoutException {
		
		log.info("Starting test ...");
		DqiRequest request=factoryObject.getAsReferenceTestRequest();
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(2);
		
		log.info("Submited the task...");
		Future<DqiExecution> fExec=executorService.runDqi(request, definition);
		
		
		log.info("Get the result...");
		DqiExecution execution=fExec.get(3, TimeUnit.MINUTES);
		
		log.info("Check the state of the database...");
		
		
		String sql="select EX_ID,EX_STATUS,ISS_ID from cmt_check_execution ex left join cmt_check_issue iss "
				+ "on ex.ex_id=iss.iss_ex_id where ex.last_user=?";
		
		
		List<Map<String,Object>> list=jdbcT.queryForList(sql,factoryObject.getDefaultLastUser());
		
		
		assertEquals("There should 1 and only one Exection record in the table. Please check what was generated for this test and also have a look at the filter from query."
				,1,list.size());
		
		
		assertEquals("The id form the database should be equal with the same from the DqiExecution Java object",execution.getId().toString(),list.get(0).get("EX_ID").toString());
		
		assertNull("The id form the database should be null for issue",list.get(0).get("ISS_ID"));
		
		assertEquals("The status of the Execution Java Object should be ["+DqiExecutionStatusType.EXCEPTION.value()+"]",DqiExecutionStatusType.EXCEPTION.value(),execution.getStatus());
		
		assertEquals("The status of the Execution Database Record should be ["+DqiExecutionStatusType.EXCEPTION.value()+"]",DqiExecutionStatusType.EXCEPTION.value(),list.get(0).get("EX_STATUS").toString());
		
		checkStartAndEndTimesCompletion(execution.getId());
				
	}

}
