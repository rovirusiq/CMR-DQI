insert into CMT_CHECK_DEFINITION (CHK_ID, CHK_NAME, CHK_DESCRIPTION, CHK_AREA,CHK_GROUP,CHK_SUBGROUP, CHK_EXECUTION_FREQUENCY, CHK_SQL, LAST_USER, CHK_CODE)
values (seq_checks.nextval, 'RN-1', 'CHECK WITH ERROR', 'AREA1','','', 'WEEKLY', 'Select 1 from dual where 1>0', 'CMR_CHECK', 'RN-1');

insert into CMT_CHECK_DEFINITION (CHK_ID, CHK_NAME, CHK_DESCRIPTION, CHK_AREA,CHK_GROUP,CHK_SUBGROUP, CHK_EXECUTION_FREQUENCY, CHK_SQL, LAST_USER, CHK_CODE)
values (seq_checks.nextval, 'RN-2', 'CHECK WITH SUCCESS', 'AREA1','','', 'WEEKLY', 'Select 1 from dual where 1<0', 'CMR_CHECK', 'RN-2');

insert into CMT_CHECK_DEFINITION (CHK_ID, CHK_NAME, CHK_DESCRIPTION, CHK_AREA,CHK_GROUP,CHK_SUBGROUP, CHK_EXECUTION_FREQUENCY, CHK_SQL, LAST_USER, CHK_CODE)
values (seq_checks.nextval, 'RN-3', 'CHECK WITH EXCEPTION', 'AREA1','','', 'WEEKLY', 'select something wrong', 'CMR_CHECK', 'RN-3');

insert into CMT_CHECK_DEFINITION (CHK_ID, CHK_NAME, CHK_DESCRIPTION, CHK_AREA,CHK_GROUP,CHK_SUBGROUP, CHK_EXECUTION_FREQUENCY, CHK_SQL, LAST_USER, CHK_CODE)
values (seq_checks.nextval, 'RN-4', 'CHECK WITH EXCEPTION', 'AREA1','','', 'WEEKLY', 'select ${something-non-existent} wrong', 'CMR_CHECK', 'RN-4');