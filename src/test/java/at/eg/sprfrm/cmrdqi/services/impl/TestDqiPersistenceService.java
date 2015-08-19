package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
public class TestDqiPersistenceService {
	
	
	private Logger log=LoggerFactory.getLogger(TestDqiPersistenceService.class);
	
	private String originalStatus;
	private String originalStatusDetails;
	private Long originalId;
	
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
	
	@Autowired
	private IDqiPersistenceService persistenceService;
	
	@After
	public void cleanupDatabase() {
		
		log.info("Preparing to modify back the database to it's original state");
		jdbcT.update("update CMT_CHECK_EXECUTION set ex_status=?, ex_status_details=? where ex_id=?",originalStatus,originalStatusDetails,originalId);
		
	}

	/*
	 * The rest of the relevant tests are in the Dao Test Cases 
	 */
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testMaxLengthForStatusDetailWithOkLength() {
		
		log.info("Starting test. Getting the Test Execution Object");
		DqiExecution execution=factoryObject.getAsReferenceTestExecution();
		
		//storing some orginal values, in order to be able to leave the database as we have found it
		originalStatus=execution.getStatus();
		originalStatusDetails=execution.getStatusDetails();
		originalId=execution.getId();
		
		
		log.info("Populate the Execution Java Object with status for Exception and status detail over maximum length");
		factoryObject.populateExecutionWithResultDetailsException(execution);
		int maxLength=4000;
		StringBuffer buff=new StringBuffer(maxLength+1);
		String sToAppend="AAAAAAAAAA";
		while (buff.length()<=maxLength) {
			buff.append(sToAppend);
		}
		
		assertTrue("The status detail length should be greater than max length",buff.length()>maxLength);
		execution.setStatusDetails(buff.toString());
		
		log.info("Calling the update method on the persistence service");
		persistenceService.updateExecutionStatus(execution);
		
		log.info("Checking results");
		
		assertEquals("The Status Detail description in Java Object should be the orginal one"
				+ ", with length greater than max length ",buff.length(),execution.getStatusDetails().length());
		
		Integer lengthInDatabase=jdbcT.queryForObject("select length(EX_STATUS_DETAILS) from cmt_check_execution where ex_id=?",Integer.class,execution.getId());
		log.info("Length in the database:"+lengthInDatabase);
		assertTrue("Length in the database should be less or equal than maximul length allowed",lengthInDatabase<=maxLength);
		
		log.info("Finishing test");
	}

}
