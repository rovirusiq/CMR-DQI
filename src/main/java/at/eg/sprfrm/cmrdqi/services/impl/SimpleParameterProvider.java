package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class SimpleParameterProvider implements IParameterProvider{
	
	private static class WrapperDqiDefinitionParameter{
		
		private DqiDefinitionParameter core;
		
		protected WrapperDqiDefinitionParameter(DqiDefinitionParameter core) {
			this.core=core;
		}
//		public String getName() {
//			return this.core.getName();
//		}
		
		public String getValue() {
			//can be extended based on different types id DqiDefinitionParameter
			return this.core.getValue();
		}
	}
	
	
	private IDqiPersistenceService persistenceService;
	private HashMap<String,WrapperDqiDefinitionParameter> repo=new HashMap<String, WrapperDqiDefinitionParameter>();

	
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/
	public SimpleParameterProvider(IDqiPersistenceService persistenceService) {
		this.persistenceService=persistenceService;
		this.acquireParameterList();
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
	private void acquireParameterList() {
		List<DqiDefinitionParameter> lst=this.persistenceService.retrieveAllParameters();
		for (Iterator<DqiDefinitionParameter> it = lst.iterator(); it.hasNext();) {
			DqiDefinitionParameter defP = it.next();
			this.repo.put(defP.getName().toUpperCase(),new WrapperDqiDefinitionParameter(defP));
		}
	}
	

	/************************************************************************************************************
	 *
	 *Public Exposed Methods
	 *
	 ************************************************************************************************************/
	@Override
	public String getValue(String parameterName) throws InvalidDefinitionParameterException {
		WrapperDqiDefinitionParameter wrp=this.repo.get(parameterName.toUpperCase());
		if (wrp==null) throw new InvalidDefinitionParameterException(parameterName);
		return wrp.getValue();
		
	}
}
