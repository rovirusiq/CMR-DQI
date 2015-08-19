package at.eg.sprfrm.cmrdqi.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("DqiDefinitionParameter")
public class DqiDefinitionParameter {

	
	private Long id;
	private DqiDefinitionParameterType type=DqiDefinitionParameterType.FIXED;
	private DqiDefinitionParameterRefreshFlagType refreshFlag=DqiDefinitionParameterRefreshFlagType.NO;
	private String name;
	private String value;
	private String lastUser;
	private Date insertTime;
	private Date updateTime;
	
	
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Getters and Setters
	 *
	 ************************************************************************************************************/
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
	 * @return the type
	 */
	public DqiDefinitionParameterType getType() {
		return type;
	}
	

	/**
	 * @return the refreshFlag
	 */
	public DqiDefinitionParameterRefreshFlagType getRefreshFlag() {
		return refreshFlag;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @return the lastUser
	 */
	public String getLastUser() {
		return lastUser;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/************************************************************************************************************
	 *
	 *Internal Methods
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Public Exposed Methods
	 *
	 ************************************************************************************************************/
}
