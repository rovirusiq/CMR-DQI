package at.eg.sprfrm.cmrdqi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("DqiExecution")
public class DqiExecution {
	
	private Long id;
	private DqiRequest request;
	private DqiDefinition definition;
	private String status;
	private String statusDetails;
	private List<DqiIssue> issueList=new ArrayList<DqiIssue>();
	private List<String> warningMessages=new ArrayList<String>();//not used at the moment
	private List<String> errorMessages=new ArrayList<String>();//not used at the moment
	private Date startTime;
	private Date endTime;
	
	/*
	 * Audit fields
	 */
	private String lastUser;
	private Date insertTime;
	private Date updateTime;
	
	/**********************************************************
	 * 
	 * Constructors
	 * 
	 *********************************************************/
	/**
	 * @param definition
	 */
	public DqiExecution(DqiDefinition dqiDefintion,DqiRequest dqiRequest) {
		super();
		this.setDefinition (dqiDefintion);
		this.setRequest(dqiRequest);
	}
	
	public DqiExecution(DqiRequest dqiRequest,DqiDefinition dqiDefintion) {
		super();
		this.setDefinition (dqiDefintion);
		this.setRequest(dqiRequest);
	}
	/**
	 * 
	 */
	public DqiExecution() {
		super();
	}
	
	/***********************************************************
	*
	* Getter and Setters
	*
	************************************************************/
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the definition
	 */
	public DqiDefinition getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(DqiDefinition dqiDefintion) {
		this.definition = dqiDefintion;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the issueList
	 */
	public List<DqiIssue> getIssueList() {
		return issueList;
	}
	/**
	 * @param issueList the issueList to set
	 */
	public void setIssueList(List<DqiIssue> issueList) {
		this.issueList = issueList;
	}
	/**
	 * @param issue The issue to add to the results
	 */
	public void addIssue(DqiIssue issue) {
		issue.setExecution(this);
		this.issueList.add(issue);
	}
	
	/**
	 * @return the warningMessages
	 */
	public List<String> getWarningMessages() {
		return warningMessages;
	}
	/**
	 * @param warningMessages the warningMessages to set
	 */
	public void setWarningMessages(List<String> warningMessages) {
		this.warningMessages = warningMessages;
	}
	/**
	 * @param warningMessage The warningMessage to add to the results
	 */
	public void addWarningMessage(String warningMessage) {
		this.warningMessages.add(warningMessage);
	}
	
	/**
	 * @return the errorMessages
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	/**
	 * @param errorMessages the errorMessages to set
	 */
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	/**
	 * @param errorMessage The error message to be added to the results
	 */
	public void addErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndTime(Date endDate) {
		this.endTime = endDate;
	}
	/**
	 * @return the statusDetails
	 */
	public String getStatusDetails() {
		return statusDetails;
	}
	/**
	 * @param statusDetails the statusDetails to set
	 */
	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}
	/**
	 * @return the lastUser
	 */
	public String getLastUser() {
		return lastUser;
	}
	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the request
	 */
	public DqiRequest getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(DqiRequest request) {
		this.request = request;
	}
/************************************************************************************************************
 *
 * Methods
 *
 ************************************************************************************************************/


}
