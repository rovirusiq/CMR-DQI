package at.eg.sprfrm.cmrdqi.services;

import java.util.List;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;

//TODO to wrap the runtime exceptions in DQIService Exceptions
public interface IDqiPersistenceService {
	
	public void createExecutionBasicDetail(DqiExecution execution) throws DqiServiceException;
	public void logExecutionStatus(DqiExecution execution) throws DqiServiceException ;
	public void logFinalExecutionStatus(DqiExecution execution) throws DqiServiceException;
	public void createRequest(DqiRequest request) throws DqiServiceException;
	public List<DqiDefinition> getAllDefinitions() throws DqiServiceException;

}
