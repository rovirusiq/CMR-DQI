insert into O_LDS_META.cmt_check_parameter(
	PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,LAST_USER
)
values (
    	seq_checks.nextval, 'FIXED', 'JUNIT-PARAM-1', 'column_A', 'N','JUNIT_DATA_PREP'
);
insert into O_LDS_META.cmt_check_parameter(
	PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,LAST_USER
)
values (
    	seq_checks.nextval, 'FIXED', 'JUNIT-PARAM-2', 'a_table', 'N','JUNIT_DATA_PREP'
);
insert into O_LDS_META.cmt_check_parameter(
	PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,LAST_USER
)
values (
    	seq_checks.nextval, 'FIXED', 'JUNIT-PARAM-3', 'column_B', 'N','JUNIT_DATA_PREP'
);
insert into O_LDS_META.cmt_check_parameter(
	PRM_ID,PRM_TYPE,PRM_KEY,PRM_VALUE,PRM_REFRESH_FLAG,LAST_USER
)
values (
    	seq_checks.nextval, 'FIXED', 'JUNIT-PARAM-4', '''3.14''', 'N','JUNIT_DATA_PREP'
);