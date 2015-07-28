package at.eg.sprfrm.cmrdqi.config;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;

import at.eg.sprfrm.cmrdqi.model.DqiFactoryObject;
import at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.services.DqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.SimpleDqiDefinitionExecutor;

public class ServicesConfig extends DaoConfig{	
	
/************************************************************************************************************
 *
 * Services
 *
 ************************************************************************************************************/
	@Bean
	public IDqiPersistenceService persistenceService() {
		return new DqiPersistenceService(requestDao,executionDao,definitionDao);
	}
	
	@Bean
	public IDqiFactoryObject factoryObject() {
		return DqiFactoryObject.createInstance();
	}
	
	@Bean
	public IDqiDefinitionExecutor executorService() {
		SimpleDqiDefinitionExecutor srv=new SimpleDqiDefinitionExecutor(
				Executors.newFixedThreadPool(1)
				, executionDao
				, definitionDao,persistenceService(),factoryObject());
		return srv;
	}
	
	
}
