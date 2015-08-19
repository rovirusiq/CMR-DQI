package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiExecutionStatusType;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject;
import at.eg.sprfrm.cmrdqi.services.DqiServiceException;
import at.eg.sprfrm.cmrdqi.services.IDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.IDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDqiDefinitionExecutor implements IDqiDefinitionExecutor {
	
	
	private static final Logger log=LoggerFactory.getLogger(SimpleDqiDefinitionExecutor.class);
	
	private final ExecutorService srv;
	private final IDqiExecutionDao executionDao;
	private final IDqiDefinitionDao definitionDao;
	private final IDqiPersistenceService persistenceService;
	private final IDefinitionEnhancer definitionEnhancerService;
	private final IDqiFactoryObject factoryObject;
	
	
/************************************************************************************************************
 *
 *Constructor
 *
 ************************************************************************************************************/
	public SimpleDqiDefinitionExecutor(ExecutorService executor,IDqiExecutionDao daoExecution
			,IDqiDefinitionDao daoDefinition,IDqiPersistenceService persistenceSrv
			,IDefinitionEnhancer defEnhancer,IDqiFactoryObject factory) {
		this.srv=executor;
		this.executionDao=daoExecution;
		this.definitionDao=daoDefinition;
		this.persistenceService=persistenceSrv;
		this.definitionEnhancerService=defEnhancer;
		this.factoryObject=factory;
	}
/************************************************************************************************************
 *
 *Private methods
 *
 ************************************************************************************************************/
	private TaskExecuteDqiDefinition createTaskForSimpleDefinition(DqiExecution execution) {
		TaskExecuteDqiDefinition task=new TaskExecuteDqiDefinition(this.executionDao, this.definitionDao
				,execution,this.persistenceService,this.definitionEnhancerService,this.factoryObject);
		return task;
	}
	
/************************************************************************************************************
 *
 *Methods for IDqiDefinitionExecutor
 *
 ************************************************************************************************************/
	@Override
	public Future<DqiExecution> runDqi(DqiRequest request,DqiDefinition definition) throws DqiServiceException {
		return this.runDqi(request, definition, false);
	}
	
	
	@Override
	public Future<DqiExecution> runDqi(DqiRequest request,DqiDefinition definition,Boolean lastTask) throws DqiServiceException {
		
		TaskExecuteDqiDefinition task=null;
		Future<DqiExecution> rsp=null;
		DqiExecution execution=null;
		
		if (request==null) throw new DqiServiceException("The DqiRequest parameter cannot be NULL");
		if (definition==null) throw new DqiServiceException("The Dqidefinition parameter cannot be NULL");
		
		
		try{
			execution=this.factoryObject.createExecutionFor(request, definition);
			this.persistenceService.createExecutionBasicDetail(execution);
			task=this.createTaskForSimpleDefinition(execution);
		} catch (RuntimeException ex) {
			log.info("Cannot create execution for the provied definition ["+definition+"]. An exception has occured:"+ex);
			
			try {
				if (execution!=null) {
					String msg=ex.toString();
					log.info("An exception occured when executing a SQL Query Defined Check:"+msg);
					execution.setStatus(DqiExecutionStatusType.EXCEPTION.value());
					execution.setStatusDetails(msg);
					this.persistenceService.updateExecutionStatus(execution);
				}
			} catch (RuntimeException ex2) {
				//nothing to do but to log it
				log.info("Cannot log the exception statis for the execution. The exception is: "+ex2);
			}
		
			throw new DqiServiceException("Cannot create execution for the provied definition ["+definition+"]",ex);
			
		}
		
		if (task!=null) {
			try {
				rsp=srv.submit(task);
				if (lastTask==true) srv.shutdown();
			} catch (RejectedExecutionException ex) {
				log.info("The check canot be sumited for execution. The exeption is: "+ex);
				throw new DqiServiceException("The check canot be sumited for execution. The exeption is: ",ex);
			}
		}
		return rsp;
		
	}
}
