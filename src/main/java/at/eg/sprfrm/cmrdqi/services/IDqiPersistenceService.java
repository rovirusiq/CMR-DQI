package at.eg.sprfrm.cmrdqi.services;

import java.util.List;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;

public interface IDqiPersistenceService {
	
	/**
	 * Saves to database the Execution.
	 * @param execution The execution to be saved.
	 * @throws DqiServiceException
	 */
	public void createExecutionBasicDetail(DqiExecution execution) throws DqiServiceException;
	/**
	 * Saves to database the Execution.
	 * @param execution he execution to be saved.
	 * @throws DqiServiceException
	 */
	public void updateExecutionStatus(DqiExecution execution) throws DqiServiceException ;
	/**
	 * Saves to database the Execution.
	 * @param execution The execution to be saved.
	 * @throws DqiServiceException
	 */
	public void updateFinalExecutionStatus(DqiExecution execution) throws DqiServiceException;
	/**
	 * Saves to database the Request.
	 * @param request
	 * @throws DqiServiceException
	 */
	public void createRequest(DqiRequest request) throws DqiServiceException;
	/**
	 * @return All the definitions from the database, filtered by the input parameters.
	 * @throws DqiServiceException
	 */
	public List<DqiDefinition> retrieveAllDefinitions(String area, String group, String subgroup) throws DqiServiceException,IllegalArgumentException;
	
	/**
	 * @return All the parameters from the database.
	 * @throws DqiServiceException
	 */
	public List<DqiDefinitionParameter> retrieveAllParameters() throws DqiServiceException;

}
