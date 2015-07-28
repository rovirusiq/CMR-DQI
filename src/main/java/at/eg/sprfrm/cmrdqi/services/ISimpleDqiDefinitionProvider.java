package at.eg.sprfrm.cmrdqi.services;

import java.util.List;

import at.eg.sprfrm.cmrdqi.config.DqiRequestContext;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;

public interface ISimpleDqiDefinitionProvider {
	
	/**
	 * @param reqContext
	 * @return The list of the definitions to be executed 
	 */
	public List<DqiDefinition> provideListOfDefinitions(DqiRequestContext reqContext);

}
