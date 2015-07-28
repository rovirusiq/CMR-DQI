package at.eg.sprfrm.cmrdqi.dao;

import static at.eg.sprfrm.cmrdqi.testutil.TestDqiUtils.checkDqiRequestAgainstRowsetAsInitialSelect;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDqiRequestAdvancedInt {
	
	private static Logger log=LoggerFactory.getLogger(TestDqiExecutionAdvancedInt.class);
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiRequestDao")
	private IDqiRequestDao dqiRequestDao;
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;

	
	@Test
	@Transactional
	public void testInsertRequest() {
		DqiRequest request=new DqiRequest();
		request.setDescription("Can be deleted. JUNIT description used during tests.");
		request.setRequesterCode("JUNIT");
		request.setMethod("test");
		request.setLastUser("JUNIT");
		//execute insert
		dqiRequestDao.insertRequest(request);
		//assertNotNull();
		SqlRowSet rowSet=jdbcT.queryForRowSet(SELECT_CHK_REQUEST,request.getId());
		checkDqiRequestAgainstRowsetAsInitialSelect(request, rowSet, true);
	}

}
