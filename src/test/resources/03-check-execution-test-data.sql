insert into CMT_CHECK_EXECUTION(
EX_ID,EX_RQ_ID,ORIG_CHK_ID,ORIG_CHK_NAME,ORIG_CHK_DESCRIPTION,ORIG_CHK_AREA,ORIG_CHK_GROUP,ORIG_CHK_SUBGROUP,ORIG_CHK_EXECUTION_FREQUENCY,ORIG_CHK_CODE,ORIG_CHK_SQL
,EX_STATUS,EX_STATUS_DETAILS,EX_START,EX_END,LAST_USER
)
select seq_checks.nextval
,(select rq_id from (select a.*,a.rowid from cmt_check_request a where last_user='JUNIT_DATA_PREP' and rq_requester_code='JUNIT' order by rowid asc) where rownum=1)
,chk_id,chk_name,chk_description,chk_area,chk_group,chk_subgroup,chk_execution_frequency,chk_code,chk_sql
,'SUCCESS','Don not delete.Execution for running Tests.',(sysdate-5*(1/24/60)),(sysdate-1.5*(1/24/60)),'JUNIT_DATA_PREP'
from cmt_check_definition where chk_code='JNT-001';