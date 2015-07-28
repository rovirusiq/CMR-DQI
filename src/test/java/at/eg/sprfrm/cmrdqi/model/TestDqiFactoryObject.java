package at.eg.sprfrm.cmrdqi.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

public class TestDqiFactoryObject extends AbstractDqiFactoryObject{
	
	
	private static final String DEFAULT_LAST_USER="JUNIT";
	private static final String EXTRACT_STATUS_DETAILS_ERROR="JUNIT generated status description for Error";
	private static final String EXTRACT_STATUS_DETAILS_SUCCESS="JUNIT generated status description for Success";
	private static final String EXTRACT_STATUS_DETAILS_EXCEPTION="JUNIT generated status description for Exception";
		
	private static final Logger log=LoggerFactory.getLogger(TestDqiFactoryObject.class);
	
	
	
	private IDqiRequestDao requestDao;
	
	private IDqiExecutionDao executionDao;
	
	private IDqiDefinitionDao definitionDao;
	
	private TestDqiHelper testHelper;
	
	
	private final DqiRequest cachedRequest;
	private final DqiExecution cachedExecution;
	private final List<DqiDefinition> cachedListDefinitions=new ArrayList<DqiDefinition>();
	
	
	
	public TestDqiFactoryObject(IDqiRequestDao daoRequest,IDqiExecutionDao daoExecution,IDqiDefinitionDao daoDefinition,TestDqiHelper tstHelper) {
		
		log.info("Startinng to initialize the TestDqiFactory Object");
		
		this.requestDao=daoRequest;
		this.executionDao=daoExecution;
		this.definitionDao=daoDefinition;
		this.testHelper=tstHelper;
		
		
		Long idRequest=testHelper.selectRequestIdForTest();
		this.cachedRequest=requestDao.selectRequestDetails(idRequest);
		
		
		Long idExecution=testHelper.selectExecutionIdForTest();
		this.cachedExecution=executionDao.selectExecutionBasicDetails(idExecution);
		
		List<Long> list=testHelper.fetchAllTestDqiDefintionsIdsFromDatabase();
		
		for (Long l : list) {
			cachedListDefinitions.add(definitionDao.selectDefinition(l));
		}
		
		log.info("Finishing to initialize the TestDqiFactory Object");
		
	}
/************************************************************************************************************
 *
 *Convienience methods to work with Test Results
 *
 ************************************************************************************************************/	
	public DqiRequest getAsReferenceTestRequest() {
		return this.cachedRequest;
	}
	
	public DqiDefinition getAsReferenceTestDefintionByIndex(int index) {
		return this.cachedListDefinitions.get(index);
	}
	
	public List<DqiDefinition> getAsReferenceTestDefinitionList() {
		return this.cachedListDefinitions;
	}
	
	public DqiExecution getAsReferenceTestExecution() {
		return this.cachedExecution;
	}
	 
/************************************************************************************************************
 *
 *Population convienience methods
 *
 ************************************************************************************************************/
	public void  populateExecutionWithResultDetailsError(DqiExecution execution) {
		
		Calendar stCal=Calendar.getInstance();
		stCal.add(Calendar.SECOND, -90);
		stCal.set(Calendar.MILLISECOND, 0);
		
		Calendar endCal=Calendar.getInstance();
		endCal.set(Calendar.MILLISECOND, 0);

		execution.setStartTime(stCal.getTime());
		execution.setEndTime(endCal.getTime());
		
		execution.setStatus(DqiExecutionStatusType.ERROR.value());
		execution.setStatusDetails(EXTRACT_STATUS_DETAILS_ERROR);
	}
	
	
	public void  populateExecutionWithResultDetailsSuccess(DqiExecution execution) {
		
		Calendar stCal=Calendar.getInstance();
		stCal.add(Calendar.SECOND, -10);
		stCal.set(Calendar.MILLISECOND, 0);
		
		Calendar endCal=Calendar.getInstance();
		endCal.set(Calendar.MILLISECOND, 0);

		execution.setStartTime(stCal.getTime());
		execution.setEndTime(endCal.getTime());
		
		execution.setStatus(DqiExecutionStatusType.SUCCESS.value());
		execution.setStatusDetails(EXTRACT_STATUS_DETAILS_SUCCESS);
	}
	
	public void  populateExecutionWithResultDetailsException(DqiExecution execution) {
		
		Calendar stCal=Calendar.getInstance();
		stCal.add(Calendar.SECOND, -25);
		stCal.set(Calendar.MILLISECOND, 0);
		
		Calendar endCal=Calendar.getInstance();
		endCal.set(Calendar.MILLISECOND, 0);

		execution.setStartTime(stCal.getTime());
		execution.setEndTime(endCal.getTime());
		
		execution.setStatus(DqiExecutionStatusType.EXCEPTION.value());
		execution.setStatusDetails(EXTRACT_STATUS_DETAILS_EXCEPTION);
	}
/************************************************************************************************************
 *
 * IDqiFactoryObject
 *
 ************************************************************************************************************/
	
	@Override
	public String getDefaultLastUser() {
		return DEFAULT_LAST_USER;
	}

	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.AbstractDqiFactoryObject#createExecutionFor(at.eg.sprfrm.cmrdqi.model.DqiRequest, at.eg.sprfrm.cmrdqi.model.DqiDefinition)
	 */
	@Override
	public DqiExecution createExecutionFor(DqiRequest request,DqiDefinition definition) {
		if (request==null) request=this.getAsReferenceTestRequest();
		if (definition==null)definition=this.getAsReferenceTestDefintionByIndex(0);
		return super.createExecutionFor(request, definition);
	}
	
	
}
