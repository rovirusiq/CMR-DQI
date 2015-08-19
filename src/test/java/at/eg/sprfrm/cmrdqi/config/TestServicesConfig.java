package at.eg.sprfrm.cmrdqi.config;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.services.IDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.impl.DefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.impl.DqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.impl.SimpleDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.impl.SimpleParameterProvider;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


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
	public TestIntegrationFactoryObject testDqiFactoryObject() {
		return new TestIntegrationFactoryObject(requestDao,executionDao,definitionDao,testHelper());
	}
	
	@Bean
	public IDqiPersistenceService persistenceService() {
		return new DqiPersistenceService(requestDao,executionDao,definitionDao,definitionParameterDao);
	}
	
	@Autowired
	private IDqiPersistenceService persistenceS;
	@Autowired
	private TestIntegrationFactoryObject factoryO;
	
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
