package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.HashMap;
import java.util.Map;

public class MockParameterProvider implements IParameterProvider {
	
	private Map<String,String> repo=new HashMap<String,String>();


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
	public void addParameter(String paramName,String paramValue) {
		this.repo.put(paramName, paramValue);
	}
	@Override
	public String getValue(String parameterName) throws InvalidDefinitionParameterException {
		String pV=this.repo.get(parameterName);
		if (null==pV) throw new InvalidDefinitionParameterException(parameterName);
		return pV;
	}
}
