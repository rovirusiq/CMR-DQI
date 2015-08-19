package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionParameterDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.services.DqiServiceException;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;

import java.util.List;

public class MockPersistenceService implements IDqiPersistenceService {
	
	private IDqiDefinitionParameterDao mckParameterDefinitionDao;
	
	public MockPersistenceService(IDqiDefinitionParameterDao mckParameterDefinitionDao) {
		this.mckParameterDefinitionDao=mckParameterDefinitionDao;
	}

	@Override
	public void createExecutionBasicDetail(DqiExecution execution)
			throws DqiServiceException {
		throw new RuntimeException("This operation has not been implemented in the mock");

	}

	@Override
	public void updateExecutionStatus(DqiExecution execution)
			throws DqiServiceException {
		throw new RuntimeException("This operation has not been implemented in the mock");

	}

	@Override
	public void updateFinalExecutionStatus(DqiExecution execution)
			throws DqiServiceException {
		throw new RuntimeException("This operation has not been implemented in the mock");

	}

	@Override
	public void createRequest(DqiRequest request) throws DqiServiceException {
		throw new RuntimeException("This operation has not been implemented in the mock");

	}

	@Override
	public List<DqiDefinition> retrieveAllDefinitions(String area,
			String group, String subgroup) throws DqiServiceException,
			IllegalArgumentException {
		throw new RuntimeException("This operation has not been implemented in the mock");
		//return null;
	}

	@Override
	public List<DqiDefinitionParameter> retrieveAllParameters()
			throws DqiServiceException {
		return this.mckParameterDefinitionDao.selectAllParameters();
	}
}
