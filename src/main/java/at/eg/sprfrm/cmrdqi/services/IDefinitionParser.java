package at.eg.sprfrm.cmrdqi.services;

import at.eg.sprfrm.cmrdqi.services.impl.ParseResult;

public interface IDefinitionParser {
	
	public ParseResult parseDefinition(String originalDefinition);

}
