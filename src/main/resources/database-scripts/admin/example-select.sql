select d.*,d.rowid from cmt_check_definition d order by CHK_ID ASC;
select p.*,p.rowid from cmt_check_parameter p;
select r.*,r.rowid from cmt_check_request r order by RQ_ID ASC;
select e.*,e.rowid from cmt_check_execution e order by EX_ID ASC;
select i.*,i.rowid from cmt_check_issue i;

--delete from cmt_check_request ex where last_user!='JUNIT_DATA_PREP';
--delete from cmt_check_execution ex where last_user!='JUNIT_DATA_PREP';
