package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.*;
import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

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
@TransactionConfiguration(defaultRollback = true)
public class TestDqiDefinitionAdvancedInt{	
	
	private Logger log=LoggerFactory.getLogger(TestDqiDefinitionAdvancedInt.class);
	
	
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiDefinitionDao")
	private IDqiDefinitionDao dqiDefintionDao;
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	
	@Test
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void testExecuteGenericQueryDefinitionWithIssues() {
		//create dummy defintition with only SQL for execution
		log.info("Started the test testExecuteGenericQueryDefinitionWithIssues");
		DqiDefinition def=new DqiDefinition();
		def.setCheck(SELECT_FOR_CHECK_WITH_ISSUES);
		Long rsp=dqiDefintionDao.executeGenericQueryDefinition(def);
		log.info("Ending the test testExecuteGenericQueryDefinitionWithIssues");
		assertTrue(rsp>0);
	}
	
	@Test
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void testExecuteGenericQueryDefinitionWithoutIssues() {
		//create dummy defintition with only SQL for execution
		DqiDefinition def=new DqiDefinition();
		def.setCheck(SELECT_FOR_CHECK_WITHOUT_ISSUES);
		Long rsp=dqiDefintionDao.executeGenericQueryDefinition(def);
		assertTrue(rsp==0);
	}

}
