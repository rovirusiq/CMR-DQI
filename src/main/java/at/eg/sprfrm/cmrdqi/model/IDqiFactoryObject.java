package at.eg.sprfrm.cmrdqi.model;

public interface IDqiFactoryObject {

	/************************************************************************************************************
	 *
	 * Public Methods
	 *
	 ************************************************************************************************************/
	public abstract DqiSimpleIssue createSimpleIssue(DqiExecution forExecution,String description);

	public abstract DqiExecution createExecutionFor(DqiRequest request, DqiDefinition definition);
	
	public abstract String getDefaultLastUser();
	
	public abstract DqiRequest createRequest(String requesterCode,String method,String description);

}