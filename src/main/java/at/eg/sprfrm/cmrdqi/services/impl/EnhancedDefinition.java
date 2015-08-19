package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.Date;

public class EnhancedDefinition extends DqiDefinition{
	
	private DqiDefinition baseDefinition;
	private boolean lateEvaluation=false;
	private DefinitionEnhancer enhancer;
	private String afterEnhancingDefintion;
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/
	/**
	 * @param baseDefinition
	 * @param afterEnhancingDefintion
	 */
	protected EnhancedDefinition(DqiDefinition baseDefinition,
			String afterEnhancingDefintion) {
		super();
		this.baseDefinition = baseDefinition;
		this.afterEnhancingDefintion = afterEnhancingDefintion;
		this.lateEvaluation=false;
	}
	/**
	 * @param baseDefinition
	 * @param parameterProviderService
	 */
	protected EnhancedDefinition(DqiDefinition baseDefinition,
			DefinitionEnhancer enhancer) {
		super();
		this.baseDefinition = baseDefinition;
		this.enhancer = enhancer;
		this.lateEvaluation=true;
	}
	/************************************************************************************************************
	 *
	 *Getters and Setters
	 *
	 ************************************************************************************************************/

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
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getId()
	 */
	@Override
	public Long getId() {
		return this.baseDefinition.getId();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getName()
	 */
	@Override
	public String getName() {
		return this.baseDefinition.getName();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getCode()
	 */
	@Override
	public String getCode() {
		return this.baseDefinition.getCode();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.baseDefinition.getDescription();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getArea()
	 */
	@Override
	public String getArea() {
		return this.baseDefinition.getArea();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getGroup()
	 */
	@Override
	public String getGroup() {
		return this.baseDefinition.getGroup();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getExecutionFrequency()
	 */
	@Override
	public String getExecutionFrequency() {
		return this.baseDefinition.getExecutionFrequency();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getCheck()
	 */
	@Override
	public String getCheck() throws InvalidDefinitionParameterException{
		//it does not need synchronization because there is a separate object per each defintion
		if (this.lateEvaluation==true) {
			this.afterEnhancingDefintion=this.enhancer.generateEnhancedDefinition(this.baseDefinition);
		}
		return this.afterEnhancingDefintion;
	}
	
	public String getOriginalCheck() {
		return this.baseDefinition.getCheck();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getLastUser()
	 */
	@Override
	public String getLastUser() {
		return this.baseDefinition.getLastUser();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#getInsertTime()
	 */
	@Override
	public Date getInsertTime() {
		return this.baseDefinition.getInsertTime();
	}
	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.DqiDefinition#toString()
	 */
	@Override
	public String toString() {
		return "EnhancedDefintion["+this.baseDefinition.toString()+"]";
	}
}
