package at.eg.sprfrm.cmrdqi.services;

import java.util.concurrent.Future;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;

public interface IDqiDefinitionExecutor {
	
	public Future<DqiExecution> runDqi(DqiRequest request,DqiDefinition definition) throws DqiServiceException;
	public void shutdown() throws DqiServiceException;
}
