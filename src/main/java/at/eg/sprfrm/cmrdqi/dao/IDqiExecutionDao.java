package at.eg.sprfrm.cmrdqi.dao;

import org.springframework.stereotype.Component;

import at.eg.sprfrm.cmrdqi.model.DqiExecution;

@Component("dqiExecutionDao")
public interface IDqiExecutionDao {
	
	public int updateExecutionBasicDetails(DqiExecution dqiExecution);
	public int createExecutionBasicDetails(DqiExecution dqiExecution);
	public int createIssue(DqiExecution dqiExecution,int issueIndex);
	public DqiExecution selectExecutionBasicDetails(Long idExecution);

}
