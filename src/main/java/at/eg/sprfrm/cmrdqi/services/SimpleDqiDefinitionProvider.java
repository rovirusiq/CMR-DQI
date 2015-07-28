package at.eg.sprfrm.cmrdqi.services;

import java.util.List;

import at.eg.sprfrm.cmrdqi.config.DqiRequestContext;
import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;

public class SimpleDqiDefinitionProvider implements
		ISimpleDqiDefinitionProvider {
	
	private final IDqiDefinitionDao definitionDao; 
	
/************************************************************************************************************
 *
 * Constructors
 *
 ************************************************************************************************************/
	public SimpleDqiDefinitionProvider(IDqiDefinitionDao pDefinitionDao) {
		this.definitionDao=pDefinitionDao;
	}
/************************************************************************************************************
 *
 *Getters and Setters
 *
 ************************************************************************************************************/
	
	
/************************************************************************************************************
 *
 * Methods from ISimpleDqiProvider
 *
 ************************************************************************************************************/
	@Override
	public List<DqiDefinition> provideListOfDefinitions(DqiRequestContext reqContext) {
		List<DqiDefinition> rspList=definitionDao.selectAllDefintions();
		return rspList;
	}

}
