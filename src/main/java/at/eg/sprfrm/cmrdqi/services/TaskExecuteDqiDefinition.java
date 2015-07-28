package at.eg.sprfrm.cmrdqi.services;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiExecutionStatusType;
import at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject;



public class TaskExecuteDqiDefinition implements Callable<DqiExecution> {
	

	private static final Logger log=LoggerFactory.getLogger(TaskExecuteDqiDefinition.class);
	
	private final DqiExecution execution;
	private final IDqiDefinitionDao definitionDao;
	private final IDqiPersistenceService persistenceService;
	private final IDqiFactoryObject factoryObject;

	
/************************************************************************************************************
 *
 *Constructor
 *
 ************************************************************************************************************/	
	protected TaskExecuteDqiDefinition(IDqiExecutionDao execDao,IDqiDefinitionDao defDao,DqiExecution ex,IDqiPersistenceService persistenceSrv,IDqiFactoryObject factory) {
		this.execution=ex;
		this.definitionDao=defDao;
		this.persistenceService=persistenceSrv; 
		this.factoryObject=factory;
	}

/************************************************************************************************************
 *
 * Interface Callable
 *
 ************************************************************************************************************/
	@Override
	public DqiExecution call() throws Exception {
		
		log.info("Start the call method for the definition:"+execution.getDefinition());
		execution.setStartTime(java.util.Calendar.getInstance().getTime());
		
		try {
			
			execution.setStatus(DqiExecutionStatusType.IN_PROGRESS.value());
			
			this.persistenceService.logExecutionStatus(execution);

			Long rsp=definitionDao.executeGenericQueryDefinition(execution.getDefinition());
			
			if (rsp==0) {
				execution.setStatus(DqiExecutionStatusType.SUCCESS.value());
			} else {
				execution.setStatus(DqiExecutionStatusType.ERROR.value());
				execution.setStatusDetails("One or more rows were returned by the query["+rsp+"]");
				execution.addIssue(factoryObject.createSimpleIssue(execution,"One or more rows were returned by the query["+rsp+"]"));
			}
			
		} catch (RuntimeException ex) {
			String msg=ex.toString();
			log.info("An exception occured when executing a SQL Query Defined Check:"+msg);
			execution.setStatus(DqiExecutionStatusType.EXCEPTION.value());
			execution.setStatusDetails(msg);
		}
		
		execution.setEndTime(java.util.Calendar.getInstance().getTime());
		
		try {
			this.persistenceService.logFinalExecutionStatus(execution);
		} catch(RuntimeException ex) {
			//nothing to do but to log and return
			log.info("Cannot log the exception statis for the execution. The exception is: "+ex);
		}
		log.info("Finsihed the call method for the definition:"+execution.getDefinition());
		return execution;
	}

}
