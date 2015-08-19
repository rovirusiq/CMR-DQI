package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.services.IDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

public class DefinitionEnhancer implements IDefinitionEnhancer {
	
	private IParameterProvider paramProviderService;
	private SimpleParameterParser paramParserService=new SimpleParameterParser();
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/

	/**
	 * @param paramProviderService
	 */
	public DefinitionEnhancer(IParameterProvider paramProviderService) {
		super();
		this.paramProviderService = paramProviderService;
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
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DqiDefinition>T enhanceDefinition(DqiDefinition defintionToEnhance,Class<T> requiredType) throws InvalidDefinitionParameterException {
		return (T) enhanceDefinition(defintionToEnhance);
	}
	
	@Override
	public DqiDefinition enhanceDefinition(DqiDefinition defintionToEnhance) throws InvalidDefinitionParameterException {
		//TODO implement logic that will distinguish between lateEvaluation of Parameters and immediateEvaluation
		String actualDefintion=generateEnhancedDefinition(defintionToEnhance);
		return new EnhancedDefinition(defintionToEnhance,actualDefintion);
	}
	
	protected String generateEnhancedDefinition(DqiDefinition defintionToEnhance) throws InvalidDefinitionParameterException {
		ParseResult prsResult=paramParserService.parseDefinition(defintionToEnhance.getCheck());
		return prsResult.getActualDefinition(paramProviderService);
	}
}
