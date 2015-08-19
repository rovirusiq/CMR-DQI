package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiExecutionAgainstRowsetAsInitialRequest;
import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiExecutionAgainstRowsetAsUpdate;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_EXECUTION;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiExecutionAdvancedInt {
	

	private Logger log=LoggerFactory.getLogger(TestDqiExecutionAdvancedInt.class);
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	
	@Autowired
	@Qualifier("dqiExecutionDao")
	private IDqiExecutionDao executionDao;
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	@Autowired
	private TestIntegrationFactoryObject factoryObject;
	
	
	

	@Test
	@Transactional
	public void testLogExecutionRequest() {
		
		/*
		 * Retrieve all DqiDefinitons for Unit Testing
		 */
		List<DqiDefinition> list=factoryObject.getAsReferenceTestDefinitionList();
	
		/*
		 * for each one of the create an Execution
		 */
		for (int i = 0; i < list.size(); i++) {
			
			DqiExecution execution= factoryObject.createExecutionFor(null, list.get(i));
			
			//create the record in the database
			assertEquals(executionDao.createExecutionBasicDetails(execution),1);
			
			//retrive the generated id
			Long generatedId=execution.getId();
			log.info("Id of execution:"+generatedId);
			
			//check the DqiExecution with what is in the database
			SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_EXECUTION,generatedId);
			checkDqiExecutionAgainstRowsetAsInitialRequest(execution, rowSet, true);
			
		}
		
	}
	
	
	@Test
	@Transactional
	public void testLogExecutionResultMainSuccess() {
		
		//retrieve the definition for which we create the execution
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		
		//create DqiExecution object for the DqiDefinition choosen
		DqiExecution execution=factoryObject.createExecutionFor(null, definition);
		
		//create Execution record in the database
		assertEquals(executionDao.createExecutionBasicDetails(execution),1);
		
		factoryObject.populateExecutionWithResultDetailsSuccess(execution);
		
		assertEquals(executionDao.updateExecutionBasicDetails(execution),1);
		
		//check against the database
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_EXECUTION,execution.getId());
		checkDqiExecutionAgainstRowsetAsUpdate(execution, rowSet, true);		
	}
	
	
	@Test
	@Transactional
	public void testLogExecutionResultMainError() {
		
		//retrieve the definition for which we create the execution
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		
		//create DqiExecution object for the DqiDefinition choosen
		DqiExecution execution=factoryObject.createExecutionFor(null, definition);
		
		//create Execution record in the database
		assertEquals(executionDao.createExecutionBasicDetails(execution),1);
		
		factoryObject.populateExecutionWithResultDetailsError(execution);
		
		assertEquals(executionDao.updateExecutionBasicDetails(execution),1);
		
		//check against the database
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_EXECUTION,execution.getId());
		checkDqiExecutionAgainstRowsetAsUpdate(execution, rowSet, true);		
	}
	
	
	@Test
	@Transactional
	public void testLogExecutionResultMainException() {
		
		//retrieve the definition for which we create the execution
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		
		//create DqiExecution object for the DqiDefinition choosen
		DqiExecution execution=factoryObject.createExecutionFor(null, definition);
		
		//create Execution record in the database
		assertEquals(executionDao.createExecutionBasicDetails(execution),1);
		
		factoryObject.populateExecutionWithResultDetailsException(execution);
		
		assertEquals(executionDao.updateExecutionBasicDetails(execution),1);
		
		//check against the database
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_EXECUTION,execution.getId());
		checkDqiExecutionAgainstRowsetAsUpdate(execution, rowSet, true);		
	}

}
