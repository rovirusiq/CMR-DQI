package at.eg.sprfrm.cmrdqi.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("DqiRequest")
public class DqiRequest {

	
    private Long id;

   
    private String description;

    
    private String requesterCode;

    
    private String method;

   
    private Date insertTime;

   
    private Date updateTime;

   
    private String lastUser;

   
   
	
	
	
	/***************************************************
	*
	* Constructors
	*
	****************************************************/
	
	public DqiRequest() {
	}



	/***************************************************
	*
	* Getters and Setters
	*
	****************************************************/


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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the requesterCode
	 */
	public String getRequesterCode() {
		return requesterCode;
	}



	/**
	 * @param requesterCode the requesterCode to set
	 */
	public void setRequesterCode(String requesterCode) {
		this.requesterCode = requesterCode;
	}


	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}


	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
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
