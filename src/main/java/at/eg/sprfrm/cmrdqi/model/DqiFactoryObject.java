package at.eg.sprfrm.cmrdqi.model;

public class DqiFactoryObject extends AbstractDqiFactoryObject {
	
	static final String DEFAULT_LAST_USER="CMR_CHECKS";
/************************************************************************************************************
 *
 *Constructors
 *
 ************************************************************************************************************/
	
	private DqiFactoryObject() {
	}
	
	
/************************************************************************************************************
 *
 * IDqifactoryObject
 *
 ************************************************************************************************************/	
	@Override
	public String getDefaultLastUser() {
		return DEFAULT_LAST_USER;
	}

/************************************************************************************************************
 *
 * Static methods
 *
 ************************************************************************************************************/	
	public static final IDqiFactoryObject createInstance() {
		return new DqiFactoryObject();
	}


	
}
