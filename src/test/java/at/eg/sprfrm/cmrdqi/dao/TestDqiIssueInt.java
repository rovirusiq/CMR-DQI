package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiIssueAgainstRowSetAsFull;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_ISSUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.UncategorizedDataAccessException;
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
import at.eg.sprfrm.cmrdqi.model.DqiIssue;
import at.eg.sprfrm.cmrdqi.model.TestDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiIssueInt {
	
	private Logger log=LoggerFactory.getLogger(TestDqiIssueInt.class);
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiDefinitionDao")
	private IDqiDefinitionDao dqiDefintionDao;
	
	
	@Autowired
	@Qualifier("dqiExecutionDao")
	private IDqiExecutionDao dqiExecutionDao;
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	@Autowired
	private TestDqiFactoryObject factoryObject;
	
	
	@Test
	@Transactional
	public void testCreateIssueSuccess() {
		
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		DqiExecution preInsertedExcutionForTest=factoryObject.createExecutionFor(null,definition);
		dqiExecutionDao.createExecutionBasicDetails(preInsertedExcutionForTest);
		
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue"));
		
		assertEquals(dqiExecutionDao.createIssue(preInsertedExcutionForTest, 0),1);
		
		assertNotNull(preInsertedExcutionForTest.getIssueList().get(0).getId());
		
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_ISSUE,preInsertedExcutionForTest.getIssueList().get(0).getId());
		checkDqiIssueAgainstRowSetAsFull(preInsertedExcutionForTest.getIssueList().get(0),rowSet,true);
	}

	@Test
	@Transactional
	public void testCreateMultipleIssuesSuccess() {
		
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		DqiExecution preInsertedExcutionForTest=factoryObject.createExecutionFor(null,definition);
		dqiExecutionDao.createExecutionBasicDetails(preInsertedExcutionForTest);
		
		
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 1"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 2"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 3"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 4"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 5"));
		
		int contor=0;
		for (Iterator<DqiIssue> it = preInsertedExcutionForTest.getIssueList().iterator(); it.hasNext();) {
			DqiIssue iss = it.next();
			assertEquals(dqiExecutionDao.createIssue(preInsertedExcutionForTest, contor),1);
			assertNotNull(preInsertedExcutionForTest.getIssueList().get(contor).getId());
			assertNotNull(iss.getId());
			contor++;
		}
		
		contor=0;
		for (Iterator<DqiIssue> it = preInsertedExcutionForTest.getIssueList().iterator(); it.hasNext();) {
			DqiIssue iss = it.next();
			SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_ISSUE,iss.getId());
			checkDqiIssueAgainstRowSetAsFull(preInsertedExcutionForTest.getIssueList().get(contor),rowSet,true);
			contor++;
		}
	}
	

	@Test(expected=org.springframework.jdbc.UncategorizedSQLException.class)
	@Transactional
	public void testCreateIssueException() {
		
		log.info("#Starting testCreateIssueException");
		
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		DqiExecution preInsertedExcutionForTest=factoryObject.createExecutionFor(null,definition);
		dqiExecutionDao.createExecutionBasicDetails(preInsertedExcutionForTest);
		
		StringBuffer buff=new StringBuffer();
		while(buff.length()<=4000){
			buff.append("AAAAAAAAAA");
		}
		
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,buff.toString()));
		log.info("#Ending testCreateIssueException - before Exception is thrown");
		try {
			dqiExecutionDao.createIssue(preInsertedExcutionForTest, 0);
			log.info("#After create issue call");
		} catch (Exception ex) {
			log.info("An exception was catched for inspection:"+ex);
			throw ex;
		}
		log.info("#End of procedure");
		
	}
	

	@Test
	@Transactional
	public void testCreateIssueExceptionAndCheckId() {
		
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		DqiExecution preInsertedExcutionForTest=factoryObject.createExecutionFor(null,definition);
		dqiExecutionDao.createExecutionBasicDetails(preInsertedExcutionForTest);
		
		StringBuffer buff=new StringBuffer();
		while(buff.length()<=4000){
			buff.append("AAAAAAAAAA");
		}
		
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,buff.toString()));
		
		try {
			dqiExecutionDao.createIssue(preInsertedExcutionForTest, 0);
		} catch (RuntimeException ex) {
			//it is expected to throw and exception
		}
		assertNotNull(preInsertedExcutionForTest.getIssueList().get(0).getId());
		
	}
	
	@Test(expected=UncategorizedDataAccessException.class)
	@Transactional
	public void testCreateMultipleIssuesExceptions() {
		
		DqiDefinition definition=factoryObject.getAsReferenceTestDefintionByIndex(0);
		DqiExecution preInsertedExcutionForTest=factoryObject.createExecutionFor(null,definition);
		dqiExecutionDao.createExecutionBasicDetails(preInsertedExcutionForTest);
		
		StringBuffer buff=new StringBuffer();
		while(buff.length()<=4000){
			buff.append("AAAAAAAAAA");
		}
	
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 1"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 2"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,buff.toString()));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 4"));
		preInsertedExcutionForTest.addIssue(factoryObject.createSimpleIssue(preInsertedExcutionForTest,"Description of the issue 5"));
		
		int contor=0;
		for (Iterator<DqiIssue> it = preInsertedExcutionForTest.getIssueList().iterator(); it.hasNext();) {
			dqiExecutionDao.createIssue(preInsertedExcutionForTest, contor);
			contor++;
		}
	}

}
