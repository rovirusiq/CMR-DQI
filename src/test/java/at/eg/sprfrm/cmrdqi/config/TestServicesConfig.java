package at.eg.sprfrm.cmrdqi.config;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;

import at.eg.sprfrm.cmrdqi.model.TestDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.services.DqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.SimpleDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;


public class TestServicesConfig extends DaoConfig {
	
/************************************************************************************************************
 *
 *Services
 *
 ************************************************************************************************************/
	@Bean
	public TestDqiHelper testHelper() {
		return new TestDqiHelper(); 
	}
	
	@Bean
	public TestDqiFactoryObject testDqiFactoryObject() {
		return new TestDqiFactoryObject(requestDao,executionDao,definitionDao,testHelper());
	}
	
	@Bean
	public IDqiPersistenceService persistenceService() {
		return new DqiPersistenceService(requestDao,executionDao,definitionDao);
	}
	
	@Bean
	public IDqiDefinitionExecutor executorService() {
		SimpleDqiDefinitionExecutor srv=new SimpleDqiDefinitionExecutor(
				Executors.newFixedThreadPool(1)
				, executionDao
				, definitionDao,persistenceService(),testDqiFactoryObject());
		return srv;
	}

}
