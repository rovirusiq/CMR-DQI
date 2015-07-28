package at.eg.sprfrm.cmrdqi.model;

import java.util.Date;

//TODO to maintain hashCode and euqals
//TODO sa sterg Id-ul daca imi da eroare la inserare issue
public abstract class DqiIssue {
	
	
	protected Long id;
	private final String type;
	private final String description;
	private Date insertTime;
	private Date updateTime;
	private String lastUser;
	
	protected DqiExecution execution;
	protected DqiRequest request;
	
	
	
	public DqiIssue(String type,String description) throws DqiModelException{
		if ((type==null) || ("".equals(type))) type="SIMPLE";
		if (!"SIMPLE".equals(type)) throw new DqiModelException("Restriction of DqiIssue violated: The type of the Issue must be SIMPLE");
		this.type=type;
		this.description=description;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the execution
	 */
	public DqiExecution getExecution() {
		return execution;
	}
	/**
	 * @param execution the execution to set
	 */
	public void setExecution(DqiExecution dqiExecution) {
		this.execution = dqiExecution;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

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
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}

	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date startTime) {
		this.insertTime = startTime;
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
	public void setUpdateTime(Date endTime) {
		this.updateTime = endTime;
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
	
	
	
}
