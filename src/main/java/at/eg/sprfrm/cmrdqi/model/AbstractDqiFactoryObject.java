package at.eg.sprfrm.cmrdqi.model;

public abstract class AbstractDqiFactoryObject implements IDqiFactoryObject{

	public AbstractDqiFactoryObject() {
		super();
	}

	/************************************************************************************************************
	 *
	 *IDqifactory
	 *
	 ************************************************************************************************************/
	@Override
	public DqiSimpleIssue createSimpleIssue(DqiExecution forExecution, String description) {
		DqiSimpleIssue iss=new DqiSimpleIssue(DqiIssueType.SIMPLE.value(),description);
		iss.setExecution(forExecution);
		iss.setLastUser(forExecution.getLastUser());		
		return iss;
	}

	@Override
	public DqiExecution createExecutionFor(DqiRequest request, DqiDefinition definition) {
		DqiExecution execution=new DqiExecution(request,definition);
		execution.setLastUser(getDefaultLastUser());
		execution.setStatus(DqiExecutionStatusType.SUBMITTED.value());
		execution.setStatusDetails("");
		return execution;
		
	}
	
	public abstract String getDefaultLastUser();

	/* (non-Javadoc)
	 * @see at.eg.sprfrm.cmrdqi.model.IDqiFactoryObject#createRequest(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DqiRequest createRequest(String requesterCode, String method,String description) {
		DqiRequest request=new DqiRequest();
		request.setRequesterCode(requesterCode);
		request.setMethod(method);
		request.setDescription(description);
		request.setLastUser(this.getDefaultLastUser());
		return request;
	}
	
	

}