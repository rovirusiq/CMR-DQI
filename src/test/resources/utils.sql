select a.*,a.rowid from cmt_check_definition a;
select r.*,r.rowid from cmt_check_request r;
select c.*,c.rowid from cmt_check_execution c order by EX_ID ASC;
select d.*,d.rowid from cmt_check_issue d;

delete from cmt_check_request ex where last_user!='JUNIT_DATA_PREP';
delete from cmt_check_execution ex where last_user!='JUNIT_DATA_PREP';
