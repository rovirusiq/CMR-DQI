package at.eg.sprfrm.cmrdqi.testutil;

import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_EXECUTION_ID;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_CHK_REQUEST_ID;
import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.SELECT_IDS_CHK_DEFINTION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;

public class TestDqiHelper {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	@Autowired
	@Qualifier("dqiDefinitionDao")
	private IDqiDefinitionDao dqiDefintionDao;
	
	
	@Autowired
	@Qualifier("dqiRequestDao")
	private IDqiRequestDao dqiRequestDao;
	
	@Autowired
	@Qualifier("dqiExecutionDao")
	private IDqiExecutionDao dqiExecutionDao;
	
	
	public List<Long> fetchAllTestDqiDefintionsIdsFromDatabase(){
		List<Long> list=jdbcT.queryForList(SELECT_IDS_CHK_DEFINTION+" where chk_code in ('JNT-001','JNT-002','JNT-003') order by CHK_ID ASC", Long.class);
		return list;
	}
	
	public Long fetchOneTestDqiDefintionIdFromDatabase(){
		Long defId=jdbcT.queryForObject(SELECT_IDS_CHK_DEFINTION+" where chk_code='JNT-001'", Long.class);
		return defId;
	}
	
	public Long selectExecutionIdForTest() {
		Long idExecution=jdbcT.queryForObject(SELECT_CHK_EXECUTION_ID, Long.class);
		return idExecution;
	}
	
	public Long selectRequestIdForTest() {
		Long idExecution=jdbcT.queryForObject(SELECT_CHK_REQUEST_ID, Long.class);
		return idExecution;
	}
	
	

}
