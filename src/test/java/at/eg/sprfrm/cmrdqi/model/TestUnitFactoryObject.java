package at.eg.sprfrm.cmrdqi.model;

public class TestUnitFactoryObject extends AbstractDqiFactoryObject {
	
	
	protected static final String DEFAULT_LAST_USER="JUNIT";

	
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/

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
	@Override
	public String getDefaultLastUser() {
		return DEFAULT_LAST_USER;
	}
	
	public DqiDefinition createDefinition(String checkDefinition) {
		DqiDefinition def=new DqiDefinition();
		def.setArea("JUNIT");
		def.setCode("JUNIT-MCK");
		def.setDescription("Generated for JUNIT tests");
		def.setId(-1L);
		def.setCheck(checkDefinition);
		return def;
	}
}
