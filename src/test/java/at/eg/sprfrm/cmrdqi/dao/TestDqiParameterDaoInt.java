package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.*;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.*;
import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameterRefreshFlagType;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameterType;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiParameterDaoInt {
	
	private static Logger log=LoggerFactory.getLogger(TestDqiParameterDaoInt.class);
	
	@Autowired
	@Qualifier("dqiDefinitionParameterDao")
	public IDqiDefinitionParameterDao definitionParameterDao;
	

	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;

	@Test
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void testAllDefinitions1() {
		
		Integer noOfParams=jdbcT.queryForObject(SELECT_CHK_PARAMETER_COUNT, Integer.class);
		
		List<DqiDefinitionParameter> lst=definitionParameterDao.selectAllParameters();
		assertEquals("The parameter list size is not correct",noOfParams,(Integer)lst.size());
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_PARAMETERS);
		checkDqiParameterAgainstRowset(lst.get(0), rowSet, true);
		//for the moment this fields are hardcoded
		assertEquals(DqiDefinitionParameterType.FIXED,lst.get(0).getType());
		assertEquals(DqiDefinitionParameterRefreshFlagType.NO,lst.get(0).getRefreshFlag());
	}
	
	@Test
	@Transactional
	public void testAllDefinitions2() {
		
		jdbcT.update(INSERT_TEST_CHK_PARAMETER);
		
		Integer noOfParams=jdbcT.queryForObject(SELECT_CHK_PARAMETER_COUNT, Integer.class);
		
		List<DqiDefinitionParameter> lst=definitionParameterDao.selectAllParameters();
		assertEquals("The parameter list size is not correct",noOfParams,(Integer)lst.size());
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_PARAMETERS);
		
		for (int i = 0; i < lst.size(); i++) {
			log.info("i:"+i);
			checkDqiParameterAgainstRowset(lst.get(i), rowSet, (i==0));
			//for the moment this fields are hardcoded
			assertEquals(DqiDefinitionParameterType.FIXED,lst.get(i).getType());
			assertEquals(DqiDefinitionParameterRefreshFlagType.NO,lst.get(i).getRefreshFlag());
			rowSet.next();
		}
		
	}
	
}
