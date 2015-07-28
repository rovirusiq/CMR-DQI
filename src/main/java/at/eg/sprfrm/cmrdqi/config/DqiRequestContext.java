package at.eg.sprfrm.cmrdqi.config;

import at.eg.sprfrm.cmrdqi.services.DqiServiceException;

public class DqiRequestContext extends AbstractDqiPropertiesProvider implements IDqiPropertiesProvider{
/************************************************************************************************************
*
* Class that will contain information about the status of the current DqiRequest
* It is an object that is passed around between different modules of the system
* It is not persisted in the database, it is not an entity of the model
* It is also provide access to different configuration options
*
************************************************************************************************************/	
	
	private static volatile DqiRequestContext instance=null;

	
	private DqiRequestContext(IDqiPropertiesProvider props) {
		this.populateFrom(props);
		
	}
	
	public synchronized static final Boolean isContextSet() {
		return (instance==null)? false:true;
	}
	
	public synchronized static final void save(IDqiPropertiesProvider properties) throws DqiServiceException {
		if (instance==null) {
			instance=new DqiRequestContext(properties);
		} else {
			throw new DqiServiceException("The context was already created and retrived. After it was created and retrievd it cannot be modified");
		}
	}
	
	public synchronized static final Boolean checkAndSave(IDqiPropertiesProvider properties) {
		if (instance==null) {
			instance=new DqiRequestContext(properties);
			return true;
		}
		return false;
	}
	
	public synchronized static final DqiRequestContext get() throws DqiServiceException {
		if (instance==null) {
			DqiConfiguration config=new DqiConfiguration();
			config.readConfigurationFromFiles();
			instance=new DqiRequestContext(config);
		}
		return instance;
	}






	


}
