package at.eg.sprfrm.cmrdqi.services;


public class InvalidDefinitionParameterException extends DqiServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/
	public InvalidDefinitionParameterException(String parameterName) {
		super("The parameter["+parameterName+"] cannot be found in the Parameters Repository");
	}
	
	

	/************************************************************************************************************
	 *
	 *Getters and Setters
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Internal Methods
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Public Exposed Methods
	 *
	 ************************************************************************************************************/
}
