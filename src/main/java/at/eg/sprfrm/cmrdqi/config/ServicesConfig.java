package at.eg.sprfrm.cmrdqi.config;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import at.eg.sprfrm.cmrdqi.model.DqiFactoryObject;
import at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.services.IDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.impl.*;

public class ServicesConfig extends DaoConfig{	
	
/************************************************************************************************************
 *
 * Services
 *
 ************************************************************************************************************/
	@Bean
	public IDqiPersistenceService persistenceService() {
		return new DqiPersistenceService(requestDao,executionDao,definitionDao,definitionParameterDao);
	}
	
	@Bean
	public IDqiFactoryObject factoryObject() {
		return DqiFactoryObject.createInstance();
	}
	
	@Autowired
	private IDqiPersistenceService persistenceS;
	@Autowired
	private IDqiFactoryObject factoryO;
	
	@Bean
	public IDefinitionEnhancer definitionEnhancer() {
		IParameterProvider paramPrvService=new SimpleParameterProvider(this.persistenceS);
		IDefinitionEnhancer defEnh=new DefinitionEnhancer(paramPrvService);
		return defEnh;
	}
	
	
	@Autowired
	private IDefinitionEnhancer defEnh;
	
	
	@Bean
	public IDqiDefinitionExecutor executorService() {
		SimpleDqiDefinitionExecutor srv=new SimpleDqiDefinitionExecutor(
				Executors.newFixedThreadPool(1)
				, executionDao
				, definitionDao,persistenceS,defEnh,factoryO);
		return srv;
	}
	
	
	
}
