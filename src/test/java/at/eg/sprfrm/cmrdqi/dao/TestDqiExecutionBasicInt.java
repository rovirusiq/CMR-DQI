package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiExecutionAgainstRowsetAsUpdate;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_EXECUTION;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiExecutionBasicInt {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiDefinitionDao")
	private IDqiDefinitionDao defintionDao;
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	@Autowired
	private TestIntegrationFactoryObject factoryObject;
	
	@Autowired
	@Qualifier("dqiExecutionDao")
	private IDqiExecutionDao executionDao;
	
	
	@Test
	@Transactional(propagation=Propagation.NEVER)
	public void testReadExecutionDetails() {
		Long idExecution=testHelper.selectExecutionIdForTest();
		DqiExecution execution=executionDao.selectExecutionBasicDetails(idExecution);
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_EXECUTION,idExecution);
		checkDqiExecutionAgainstRowsetAsUpdate(execution, rowSet, true);
		assertNotNull(execution.getRequest());
	}

}
