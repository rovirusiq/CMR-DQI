package at.eg.sprfrm.cmrdqi;

import at.eg.sprfrm.cmrdqi.config.ApplicationConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.services.DqiServiceException;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Forest {
	
	protected Forest() {
		
	}
	
	public static Logger log=LoggerFactory.getLogger(Forest.class);
		
	public void run(String area, String group, String subgroup,String requesterCode, String method, String description) throws DqiServiceException{
		
		log.info("Running with parameters:[area="+area+",group="+group+",subgroup="+subgroup+",requestCode="+requesterCode+",method="+method+",description="+description+"]");
		
		try(AbstractApplicationContext context=new AnnotationConfigApplicationContext(ApplicationConfig.class)){
			
			IDqiPersistenceService persistenceService=context.getBean(IDqiPersistenceService.class);
			IDqiDefinitionExecutor executorService=context.getBean(IDqiDefinitionExecutor.class);
			IDqiFactoryObject factoryObject=context.getBean(IDqiFactoryObject.class);
			
			log.info("Retrieving all DqiDefinitions.");
			List<DqiDefinition> list=persistenceService.retrieveAllDefinitions(area,group,subgroup);
			
			log.info("Create request");
			DqiRequest request=factoryObject.createRequest(requesterCode, method,description);
			persistenceService.createRequest(request);
			
			
			//list of resutls
			List<Future<DqiExecution>> listResults=new ArrayList<Future<DqiExecution>>();
			
			log.info("Start processing definitions");
			int count=1;
			for (Iterator<DqiDefinition> it = list.iterator(); it.hasNext();) {
				Boolean lastTask=(count==list.size());
				DqiDefinition definition = (DqiDefinition) it.next();
				
				log.info("Submitting definition: "+definition);
				
				Future<DqiExecution> rsp=executorService.runDqi(request, definition,lastTask);
				listResults.add(rsp);
				count++;
			}
			
			boolean notFinished=true;
			
			while (notFinished) {
				
				log.info("Checking results");
				notFinished=false;
				
				for (Iterator<Future<DqiExecution>> it = listResults.iterator(); it.hasNext();) {
					Future<DqiExecution> ft=it.next();
					if (!ft.isDone()) notFinished=true;
					break;
				}
				log.info("All results are ready:"+(!notFinished));
				try{
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					//go on, go further
				}
			}
			
			log.info("Finished.");			
		}		
	}

}
