package at.eg.sprfrm.cmrdqi.testutil;

public class TestQueriesDefinitions {
	
	/***************************************************
	*
	* Queries for DEFINITION, EXECUTION and ISSUES
	*
	****************************************************/
	
	public static final String SELECT_CHK_DEFINITION = ""
					+"select CHK_ID,CHK_NAME,CHK_CODE,CHK_DESCRIPTION"
					+",CHK_AREA,CHK_GROUP,CHK_SUBGROUP,CHK_EXECUTION_FREQUENCY,CHK_SQL"
					+",LAST_USER,INSERT_TIME,UPDATE_TIME"
					+" from CMT_CHECK_DEFINITION def where def.chk_id=?";
	public static final String SELECT_IDS_CHK_DEFINTION = "select CHK_ID from CMT_CHECK_DEFINITION";
	
	public static final String SELECT_CHK_EXECUTION=""
					+"select EX_ID,ORIG_CHK_ID,ORIG_CHK_NAME,ORIG_CHK_DESCRIPTION"
					+",ORIG_CHK_AREA,ORIG_CHK_GROUP,ORIG_CHK_SUBGROUP"
					+",ORIG_CHK_EXECUTION_FREQUENCY,ORIG_CHK_CODE,ORIG_CHK_SQL"
					+",EX_STATUS,EX_STATUS_DETAILS,EX_START,EX_END"
					+",ex.INSERT_TIME,ex.UPDATE_TIME,ex.LAST_USER"
					+",RQ_ID as P_RQ_ID, RQ_DESC as P_RQ_DESC"
					+",RQ_REQUESTER_CODE as P_RQ_REQUESTER_CODE"
					+",RQ_METHOD as P_RQ_METHOD"
					+",rq.INSERT_TIME as P_INSERT_TIME"
					+",rq.UPDATE_TIME as P_UPDATE_TIME"
					+",rq.LAST_USER as P_LAST_USER"
					+" from CMT_CHECK_EXECUTION ex inner join CMT_CHECK_REQUEST rq on ex.ex_rq_id=rq.rq_id where ex.ex_id=?";
	
	public static final String SELECT_CHK_EXECUTION_ID="select EX_ID from("
				+ "select * from CMT_CHECK_EXECUTION where last_user='JUNIT_DATA_PREP' and orig_chk_code='JNT-001'"
				+ ") ";

	public static final String SELECT_CHK_ISSUE="select ISS_ID,ISS_EX_ID,ISS_DESCRIPTION,ISS_TYPE "
					+ ",iss.LAST_USER,iss.INSERT_TIME,iss.UPDATE_TIME"
					+ " from CMT_CHECK_ISSUE iss where iss.ISS_ID=?";
	
	public static final String SELECT_CHK_REQUEST_ID="select rq_id from ("
					+"select a.*,a.rowid from cmt_check_request a where last_user='JUNIT_DATA_PREP' and rq_requester_code='JUNIT' order by rowid asc"
					+ ") where rownum=1";
	
	public static final String SELECT_CHK_REQUEST=""
			+"select RQ_ID, RQ_DESC, RQ_REQUESTER_CODE, RQ_METHOD, rq.INSERT_TIME, rq.UPDATE_TIME, rq.LAST_USER"
			+" from CMT_CHECK_REQUEST rq where rq.RQ_ID=?";
	
	public static final String SELECT_FOR_CHECK_WITH_ISSUES="select 1 from dual where 1>0";
	
	public static final String SELECT_FOR_CHECK_WITHOUT_ISSUES="select 1 from dual where 1<0";
	
	public static final String SELECT_FOR_CHECK_WITH_DAO_EXCEPTION="select 1#gsfs from adada where bla>xc";
	
	public static final String SELECT_CHK_PARAMETERS="select" 
			+" PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,INSERT_TIME,UPDATE_TIME,LAST_USER"
			+" from cmt_check_parameter param order by PRM_ID ASC";
	
	public static final String INSERT_TEST_CHK_PARAMETER="insert into cmt_check_parameter" 
			+"(PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,LAST_USER)"
			+" values(seq_checks.nextval, 'FIXED', 'PARAM-TEST-2', '3.14-2', 'N','JUNIT_DATA')";
	
	public static final String SELECT_CHK_PARAMETER_COUNT="select count(*) from cmt_check_parameter"; 
	
	public static final String SELECT_PARAMETER_VALUE_1="select PRM_VALUE from cmt_check_parameter t where "
			+ "last_user='JUNIT_DATA_PREP' and prm_key='"+TestDqiHelper.PARAMETER_NAME_1+"'";
	
	public static final String INSERT_CHECK_DEFINITION="insert into O_LDS_META.CMT_CHECK_DEFINITION"
			+ " (CHK_ID, CHK_NAME, CHK_DESCRIPTION, CHK_AREA,CHK_GROUP,CHK_SUBGROUP, CHK_EXECUTION_FREQUENCY, CHK_SQL, LAST_USER, CHK_CODE)"
			+ " values (seq_checks.nextval, ?, 'If you see this an error has occured.Inserted for JUNIT testing, it should be deleted after the tests are run',?,?,?, 'DAILY',?, 'JUNIT', ?)";
	
}