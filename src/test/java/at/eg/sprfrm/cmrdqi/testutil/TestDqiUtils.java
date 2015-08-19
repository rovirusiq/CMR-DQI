package at.eg.sprfrm.cmrdqi.testutil;

import static at.eg.sprfrm.cmrdqi.testutil.TestJdbcUtils.convertClobToString;
import static at.eg.sprfrm.cmrdqi.testutil.TestJdbcUtils.convertOracleTimestampToDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiExecutionStatusType;
import at.eg.sprfrm.cmrdqi.model.DqiIssue;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;


//TODO to merge this class with the helper after I identify a better method to do the check
public class TestDqiUtils {
	
	private static String DATABASE_DEFAULT_USER="SYSTEM";
		
	public static final void checkDqiExecutionAgainstRowsetCommon(DqiExecution execution,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		
		if (moveRowSetToFirst) rowSet.first();
		
		DqiDefinition definition=execution.getDefinition();
		DqiRequest request=execution.getRequest();
		
		assertEquals(execution.getId(),(Long)rowSet.getLong("EX_ID"));
		assertEquals(definition.getId(),(Long)rowSet.getLong("ORIG_CHK_ID"));
		assertEquals(definition.getName(),rowSet.getString("ORIG_CHK_NAME"));
		assertEquals(definition.getDescription(),rowSet.getString("ORIG_CHK_DESCRIPTION"));
		assertEquals(definition.getArea(),rowSet.getString("ORIG_CHK_AREA"));
		assertEquals(definition.getGroup(),rowSet.getString("ORIG_CHK_GROUP"));
		assertEquals(definition.getSubGroup(),rowSet.getString("ORIG_CHK_SUBGROUP"));
		assertEquals(definition.getExecutionFrequency(),rowSet.getString("ORIG_CHK_EXECUTION_FREQUENCY"));
		assertEquals(definition.getCode(),rowSet.getString("ORIG_CHK_CODE"));
		assertEquals(definition.getCheck().trim(),convertClobToString(rowSet, "ORIG_CHK_SQL").trim());
		assertNotNull(convertOracleTimestampToDate(rowSet, "INSERT_TIME"));
		
		assertEquals(request.getId(),(Long)rowSet.getLong("P_RQ_ID"));
		assertEquals(request.getDescription(),rowSet.getString("P_RQ_DESC"));
		assertEquals(request.getRequesterCode(),rowSet.getString("P_RQ_REQUESTER_CODE"));
		assertEquals(request.getMethod(),rowSet.getString("P_RQ_METHOD"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "P_INSERT_TIME"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "P_UPDATE_TIME"));
		assertNotEquals(DATABASE_DEFAULT_USER,rowSet.getString("LAST_USER"));
		assertNotEquals(DATABASE_DEFAULT_USER,rowSet.getString("P_LAST_USER"));
		
	}
	
	public static final void checkDqiExecutionAgainstRowsetAsInitialRequest(DqiExecution execution,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		
		if (moveRowSetToFirst) rowSet.first();
		checkDqiExecutionAgainstRowsetCommon(execution, rowSet, false);
		assertEquals(DqiExecutionStatusType.SUBMITTED.value(),rowSet.getString("EX_STATUS"));
		
	}
	
	public static final void checkDqiExecutionAgainstRowsetAsUpdate(DqiExecution execution,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		
		if (moveRowSetToFirst) rowSet.first();
		checkDqiExecutionAgainstRowsetCommon(execution, rowSet, false);
		assertEquals(execution.getStatus(),rowSet.getString("EX_STATUS"));
		assertEquals(execution.getStatusDetails(),rowSet.getString("EX_STATUS_DETAILS"));
		assertEquals(execution.getStartTime(), convertOracleTimestampToDate(rowSet, "EX_START"));
		assertEquals(execution.getEndTime(), convertOracleTimestampToDate(rowSet, "EX_END"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "UPDATE_TIME"));
		
	}
	
	
	
	public static void checkDqiDefinitionAgainstRowSetAsFull(DqiDefinition definition,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		if (moveRowSetToFirst) rowSet.first();
		
		assertEquals(definition.getId(), (Long)rowSet.getLong("CHK_ID"));
		assertEquals(definition.getName(), rowSet.getString("CHK_NAME"));
		assertEquals(definition.getCode(), rowSet.getString("CHK_CODE"));
		assertEquals(definition.getDescription(), rowSet.getString("CHK_DESCRIPTION"));
		assertEquals(definition.getArea(), rowSet.getString("CHK_AREA"));
		assertEquals(definition.getGroup(), rowSet.getString("CHK_GROUP"));
		assertEquals(definition.getSubGroup(), rowSet.getString("CHK_SUBGROUP"));
		assertEquals(definition.getExecutionFrequency(), rowSet.getString("CHK_EXECUTION_FREQUENCY"));
		assertEquals(definition.getCheck().trim(), convertClobToString(rowSet,"CHK_SQL").trim());
		assertEquals(definition.getLastUser(), rowSet.getString("LAST_USER"));
		assertEquals(definition.getInsertTime(), convertOracleTimestampToDate(rowSet, "INSERT_TIME"));
		assertEquals(definition.getUpdateTime(), convertOracleTimestampToDate(rowSet, "UPDATE_TIME"));
		
	}
	
	
	public static final void checkDqiIssueAgainstRowSetAsFull(DqiIssue issue,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		if (moveRowSetToFirst) rowSet.first();
		
		assertEquals(issue.getId(), (Long)rowSet.getLong("ISS_ID"));
		assertEquals(issue.getDescription(), rowSet.getString("ISS_DESCRIPTION"));
		assertEquals(issue.getExecution().getId(), (Long)rowSet.getLong("ISS_EX_ID"));
		assertEquals(issue.getType(), rowSet.getString("ISS_TYPE"));
		assertNotNull(rowSet.getString("LAST_USER"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "INSERT_TIME"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "UPDATE_TIME"));
		
	}
	
	
	public static final void checkDqiRequestAgainstRowsetAsInitialSelect(DqiRequest request,SqlRowSet rowSet, boolean moveRowSetToFirst) {
		
		if (moveRowSetToFirst) rowSet.first();
		
		assertEquals(request.getId(), (Long)rowSet.getLong("RQ_ID"));
		assertEquals(request.getDescription(), rowSet.getString("RQ_DESC"));
		assertEquals(request.getRequesterCode(), rowSet.getString("RQ_REQUESTER_CODE"));
		assertEquals(request.getMethod(), rowSet.getString("RQ_METHOD"));
		assertNotNull(rowSet.getString("LAST_USER"));
		assertNotEquals(DATABASE_DEFAULT_USER, rowSet.getString("LAST_USER"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "INSERT_TIME"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "UPDATE_TIME"));
				
	}
	
public static final void checkDqiParameterAgainstRowset(DqiDefinitionParameter parameter,SqlRowSet rowSet,boolean moveRowSetToFirst) {
		
		if (moveRowSetToFirst) rowSet.first();
		
		assertEquals(parameter.getId(),(Long)rowSet.getLong("PRM_ID"));
		assertEquals(parameter.getName(),rowSet.getString("PRM_KEY"));
		assertEquals(parameter.getValue(),rowSet.getString("PRM_VALUE"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "INSERT_TIME"));
		assertNotNull(convertOracleTimestampToDate(rowSet, "UPDATE_TIME"));
		assertNotEquals(DATABASE_DEFAULT_USER,rowSet.getString("LAST_USER"));
		
	}

}
