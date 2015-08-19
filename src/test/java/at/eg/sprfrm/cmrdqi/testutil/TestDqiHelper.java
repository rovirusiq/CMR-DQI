package at.eg.sprfrm.cmrdqi.testutil;

import static at.eg.sprfrm.cmrdqi.testutil.TestQueriesDefinitions.*;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDqiHelper {
	
	
	
	public static final String PARAMETER_NAME_1="JUNIT-PARAM-1";
	
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
		List<Long> list=jdbcT.queryForList(SELECT_IDS_CHK_DEFINTION+" where last_user='JUNIT_DATA_PREP' order by CHK_ID ASC", Long.class);
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
	
	public String selectValueForParameter() {
		String rsp=jdbcT.queryForObject(SELECT_PARAMETER_VALUE_1,String.class);
		return rsp;
	}

}
