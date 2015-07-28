select * from cmt_check_definition a;
select * from cmt_check_request b;
select c.*,c.rowid from cmt_check_execution c order by EX_ID ASC;
select d.*,d.rowid from cmt_check_issue d;

delete from cmt_check_execution ex where last_user!='JUNIT_DATA_PREP';