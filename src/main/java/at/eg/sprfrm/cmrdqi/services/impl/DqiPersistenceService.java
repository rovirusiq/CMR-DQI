package at.eg.sprfrm.cmrdqi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionParameterDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;
import at.eg.sprfrm.cmrdqi.model.DqiExecution;
import at.eg.sprfrm.cmrdqi.model.DqiRequest;
import at.eg.sprfrm.cmrdqi.services.DqiServiceException;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;

import java.util.List;

public class DqiPersistenceService implements IDqiPersistenceService{
	
	
	private static final Logger log=LoggerFactory.getLogger(DqiPersistenceService.class);
	
	private static final String EXCEPTION_MESSAGE = "An exception occured in the persistence service";
	private final IDqiExecutionDao executionDao;
	private final IDqiRequestDao requestDao;
	private final IDqiDefinitionDao definitionDao;
	private final IDqiDefinitionParameterDao definitionParameterDao;

	/**
	 * @param executionDao
	 * @param definitionDao
	 */
	public DqiPersistenceService(IDqiRequestDao requestDao,IDqiExecutionDao executionDao,IDqiDefinitionDao definitionDao,IDqiDefinitionParameterDao definitionParameterDao) {
		super();
		this.executionDao = executionDao;
		this.requestDao=requestDao;
		this.definitionDao=definitionDao;
		this.definitionParameterDao=definitionParameterDao;
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
	private boolean parameterHasValue(String paramToCheck, boolean emptyStringIsNull) {
		if (emptyStringIsNull && "".equals(paramToCheck) ) {
			paramToCheck=null;
		}
		return !(null==paramToCheck);
	}
	
	private String toUpperCase(String s) {
		if (s!=null) s=s.toUpperCase();
		return s;
	}
/************************************************************************************************************
 *
 *IDqiPersistenceServer
 *
 ************************************************************************************************************/
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createExecutionBasicDetail(DqiExecution execution) throws DqiServiceException {
		try {
			executionDao.createExecutionBasicDetails(execution);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateExecutionStatus(DqiExecution execution) throws DqiServiceException {
		try {
			this.wrapperForUpdateBasicDetails(execution);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateFinalExecutionStatus(DqiExecution execution) throws DqiServiceException{
		try {
			this.wrapperForUpdateBasicDetails(execution);
			if (execution.getIssueList().size()>0) {
				this.executionDao.createIssue(execution, 0);
			}
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createRequest(DqiRequest request) throws DqiServiceException {
		try {
			requestDao.insertRequest(request);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}	
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<DqiDefinition> retrieveAllDefinitions(String area, String group, String subgroup) throws DqiServiceException, IllegalArgumentException{
		
		boolean paramsAreOk=true;
		
		log.info("Param subgroup has value evaluation: "+parameterHasValue(subgroup, true));
		log.info("Param group has value evaluation: "+parameterHasValue(group, true));
		log.info("Param area has value evaluation: "+parameterHasValue(area, true));
		
		
		if (parameterHasValue(subgroup, true)) {
			paramsAreOk&=parameterHasValue(group, true);
		}
		
		if (parameterHasValue(group, true)) {
			paramsAreOk&=parameterHasValue(area, true);
		}
		
		if (!paramsAreOk) throw new IllegalArgumentException("Illegal arguments for area, group, subgroup");
		
		area=toUpperCase(area);
		group=toUpperCase(group);
		subgroup=toUpperCase(subgroup);
		
		try {
			return this.definitionDao.selectAllDefintions(area,group,subgroup);
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<DqiDefinitionParameter> retrieveAllParameters() throws DqiServiceException {
		log.info("Start method retrieveAllParameters");
		try {
			List<DqiDefinitionParameter>  lst=this.definitionParameterDao.selectAllParameters();
			log.info("End method retrieveAllParameters");
			return lst;
		} catch(RuntimeException ex) {
			throw new DqiServiceException(EXCEPTION_MESSAGE,ex);
		}
	}

}
