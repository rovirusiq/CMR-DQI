package at.eg.sprfrm.cmrdqi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.eg.sprfrm.cmrdqi.config.DqiConfiguration;
import at.eg.sprfrm.cmrdqi.config.DqiRequestContext;
import at.eg.sprfrm.cmrdqi.services.DqiServiceException;

public class DqiChecks {
	
	public static Logger log=LoggerFactory.getLogger(DqiChecks.class);
	public static String DEFAULT_REQUESTER_METHOD=RequestMethodType.CMD_LINE.value();
	public static String DEFAULT_REQUESTER_DESCRIPTION="No description was provided";
	
	
	private Forest ft=new Forest();
	
	
	private DqiChecks() {
		
	}
	
	private boolean isValidInputParam(String param) {
		if ((null==param) || ("".equals(param))){
			return false;
		} else {
			return true;
		}
	}
	
	
	public void processRequest(String requesterCode, String description) throws DqiServiceException{
		
		log.info("Checking parameteres");
		if (!isValidInputParam(requesterCode)) throw new DqiServiceException("Illegal Argument as requester Code");
		if (!isValidInputParam(description))  description=DEFAULT_REQUESTER_DESCRIPTION;
		log.info("Launching processing");
		ft.run("","","",requesterCode,RequestMethodType.JAVA_API.value(),description);
		log.info("End processing");
		
	}
	
	public void processRequest(String requesterCode, String description,
			String dbUrl,String dbUsername, String dbPassword) throws DqiServiceException{
		
		log.info("Checking parameteres");
		if (!isValidInputParam(requesterCode)) throw new DqiServiceException("Illegal Argument as requester Code");
		if (!isValidInputParam(description))  description=DEFAULT_REQUESTER_DESCRIPTION;
		if (!isValidInputParam(dbUrl)) throw new DqiServiceException("Illegal Argument as dbUrl");
		if (!isValidInputParam(dbUsername)) throw new DqiServiceException("Illegal Argument as dbUsername");
		if (!isValidInputParam(dbPassword)) throw new DqiServiceException("Illegal Argument as dbPassword");
		
		DqiConfiguration configuration=new DqiConfiguration();
		configuration.setDatabaseUrl(dbUrl);
		configuration.setDatabaseUsername(dbUsername);
		configuration.setDatabasePassword(dbPassword);
		boolean contextWasSet=!DqiRequestContext.checkAndSave(configuration);
		if (contextWasSet) {
			log.warn("Context was already save in aprevious step. The new save was ignored");
		}
		log.info("Launching processing");
		ft.run("","","",requesterCode,RequestMethodType.JAVA_API.value(),description);
		log.info("End processing");
		
		
	}

}
