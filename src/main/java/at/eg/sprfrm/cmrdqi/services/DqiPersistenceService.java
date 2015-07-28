package at.eg.sprfrm.cmrdqi.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;

import java.util.List;

public class DqiPersistenceService implements IDqiPersistenceService{
	
	private static final String EXCEPTION_MESSAGE = "An exception occured in the persistence service";
	private final IDqiExecutionDao executionDao;
	private final IDqiRequestDao requestDao;
	private final IDqiDefinitionDao definitionDao;

	/**
	 * @param executionDao
	 * @param definitionDao
	 */
	public DqiPersistenceService(IDqiRequestDao requestDao,IDqiExecutionDao executionDao,IDqiDefinitionDao definitionDao) {
		super();
		this.executionDao = executionDao;
		this.requestDao=requestDao;
		this.definitionDao=definitionDao;
	}
/************************************************************************************************************
 *
 *Private
 *
 ************************************************************************************************************/
	private void wrapperForUpdateBasicDetails(DqiExecution execution) {
		
		String safeKeep=execution.getStatusDetails();
		
		int maxLength=4000;
		boolean changeBack=false;
		if (safeKeep.length()>maxLength) {
			changeBack=true;
			execution.setStatusDetails(execution.getStatusDetails().substring(0,maxLength));
		}
		try {
			executionDao.updateExecutionBasicDetails(execution);
		} finally {
			if (changeBack) execution.setStatusDetails(safeKeep);
		}
		
	}
/************************************************************************************************************
 *
 *IDqiPersistenceServer
 *
 ************************************************************************************************************/
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createExecutionBasicDetail(DqiExecution execution) throws DqiServiceException {
		try {
			executionDao.createExecutionBasicDetails(execution);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void logExecutionStatus(DqiExecution execution) throws DqiServiceException {
		try {
			this.wrapperForUpdateBasicDetails(execution);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void logFinalExecutionStatus(DqiExecution execution) throws DqiServiceException{
		try {
			this.wrapperForUpdateBasicDetails(execution);
			if (execution.getIssueList().size()>0) {
				this.executionDao.createIssue(execution, 0);
			}
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createRequest(DqiRequest request) throws DqiServiceException {
		try {
			requestDao.insertRequest(request);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	public List<DqiDefinition> getAllDefinitions() throws DqiServiceException{
		try {
			return this.definitionDao.selectAllDefintions();
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}

}
