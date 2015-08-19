package at.eg.sprfrm.cmrdqi.services;

import java.util.concurrent.Future;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;

public interface IDqiDefinitionExecutor {
	
	/**
	 * Receives and executes a definition in the context of a Request.
	 * @param request The DqiRequest under which the definition should be executed
	 * @param definition The DqiDefinition which must be executed
	 * @return a Future with the DqiExecution. The DqiExecution contains information about the status of the Execution.
	 * @throws DqiServiceException
	 */
	public Future<DqiExecution> runDqi(DqiRequest request,DqiDefinition definition) throws DqiServiceException;
	
	/**
	 * @see #runDqi(DqiRequest, DqiDefinition)
	 * @param request The DqiRequest under which the definition should be executed
	 * @param definition The DqiDefinition which must be executed
	 * @param lastTask True if this last task, false if it is NOT the last task submited.
	 * @return a Future with the DqiExecution. The DqiExecution contains information about the status of the Execution.
	 * @throws DqiServiceException
	 */
	public Future<DqiExecution> runDqi(DqiRequest request,DqiDefinition definition,Boolean lastTask) throws DqiServiceException;
	
}
