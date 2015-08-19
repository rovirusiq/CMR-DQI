package at.eg.sprfrm.cmrdqi.services;


public interface IParameterProvider {
	
	public String getValue(String parameterName) throws InvalidDefinitionParameterException;

}
