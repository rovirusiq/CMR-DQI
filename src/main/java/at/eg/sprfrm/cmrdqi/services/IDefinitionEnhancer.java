package at.eg.sprfrm.cmrdqi.services;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;

public interface IDefinitionEnhancer {
	
	public DqiDefinition enhanceDefinition(DqiDefinition defintionToEnhance);
	public <T extends DqiDefinition> T enhanceDefinition(DqiDefinition defintionToEnhance,Class<T> requiredType);

}
