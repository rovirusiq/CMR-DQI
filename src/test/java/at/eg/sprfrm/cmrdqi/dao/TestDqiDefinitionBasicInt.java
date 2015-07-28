package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiDefinitionAgainstRowSetAsFull;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_DEFINITION;

import java.sql.SQLException;
import java.util.Iterator;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiDefinitionBasicInt {
	
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
	public void testReadOnAllDefinitionFromDatabase() throws SQLException {
		List<Long> list=testHelper.fetchAllTestDqiDefintionsIdsFromDatabase();
		
		for (Iterator<Long> it = list.iterator(); it.hasNext();) {
			Long id = it.next();
			
			log.debug("Running test for defintion with id:"+id);
			
			DqiDefinition dqiDef=dqiDefintionDao.selectDefinition(id);
			SqlRowSet rslt=jdbcT.queryForRowSet(SELECT_CHK_DEFINITION,id);
			checkDqiDefinitionAgainstRowSetAsFull(dqiDef, rslt, true);

		}
	}

}
