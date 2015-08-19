package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiDefinitionAgainstRowSetAsFull;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.*;
import static org.junit.Assert.*;

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
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters1() {
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions();
		assertEquals("Size for getting all defintions whithout criteria should be 0",0,lst.size());
		
		lst=dqiDefintionDao.selectAllDefintions("AREA1","","");
		assertEquals("Size for getting all defintions with criteria A",0,lst.size());
	}
	
	
	private void insertChecks() {
		jdbcT.update(INSERT_CHECK_DEFINITION,"JUNIT-CHK-999","AREA1","GROUP1","SUBGROUP1","select 1 from dual where 1<0","JNT-999");
		jdbcT.update(INSERT_CHECK_DEFINITION,"JUNIT-CHK-998","AREA1","GROUP1","SUBGROUP2","select 1 from dual where 1<0","JNT-998");
		jdbcT.update(INSERT_CHECK_DEFINITION,"JUNIT-CHK-997","AREA1","GROUP1","SUBGROUP3","select 1 from dual where 1<0","JNT-997");
		jdbcT.update(INSERT_CHECK_DEFINITION,"JUNIT-CHK-996","AREA1","GROUP2","SUBGROUP1","select 1 from dual where 1<0","JNT-996");
		jdbcT.update(INSERT_CHECK_DEFINITION,"JUNIT-CHK-995","AREA2","GROUP1","SUBGROUP1","select 1 from dual where 1<0","JNT-995");
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters2() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA1","","");
		assertEquals("Size if the retruned list does not match expectations",4,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters3() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA2","","");
		assertEquals("Size if the retruned list does not match expectations",1,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters4() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA2","GROUP1","");
		assertEquals("Size if the retruned list does not match expectations",1,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters5() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA2","GROUP2","");
		assertEquals("Size if the retruned list does not match expectations",0,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters6() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA2",null,null);
		assertEquals("Size if the retruned list does not match expectations",1,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters7() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA1","GROUP1",null);
		assertEquals("Size if the retruned list does not match expectations",3,lst.size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testSelectWithParameters8() {
		insertChecks();
		List<DqiDefinition> lst=dqiDefintionDao.selectAllDefintions("AREA1","GROUP1","SUBGROUP2");
		assertEquals("Size if the retruned list does not match expectations",1,lst.size());
	}

}
