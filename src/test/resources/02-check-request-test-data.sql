insert into CMT_CHECK_REQUEST(
	RQ_ID, RQ_DESC, RQ_REQUESTER_CODE,RQ_METHOD, LAST_USER
)
values (
    	seq_checks.nextval, 'Do not delete. JUNIT Request for Testing purposes.', 'JUNIT', 'test', 'JUNIT_DATA_PREP'
);