package at.eg.sprfrm.cmrdqi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.eg.sprfrm.cmrdqi.config.DqiConfiguration;
import at.eg.sprfrm.cmrdqi.config.DqiRequestContext;

public class ShellRunner {
	
	public static Logger log=LoggerFactory.getLogger(ShellRunner.class);
	public static String DEFAULT_REQUESTER_CODE="HMN";
	public static String DEFAULT_REQUESTER_METHOD=RequestMethodType.CMD_LINE.value();
	public static String DEFAULT_REQUESTER_DESCRIPTION="No description was provided";


	public static void main(String[] args) {
		
		log.info("Checkin parameters");
		DqiConfiguration configuration=new DqiConfiguration();
		configuration.readConfigurationFromFiles();
		
		String REQ_CODE=DEFAULT_REQUESTER_CODE;
		String REQ_MTHD=DEFAULT_REQUESTER_METHOD;
		String REQ_DESC=DEFAULT_REQUESTER_DESCRIPTION;
		
		
		if (args.length==0) {
			log.info("No paramter was provided. Defaults will be used");
		} else if (args.length==2) {
			log.info("2 paramters provided. They will be read as REQUESTER_CODE, and REQUESTER_DESCRIPTION");
			REQ_CODE=args[0].trim();
			REQ_DESC=args[1].trim();
		} else if (args.length==5) {
			log.info("5 paramters provided. They will be read as REQUESTER_CODE, REQUEST_DESCRIPTION, DATABASE_URL, DATABASE_USERNAME,DATABASE_PASSWORD");
			REQ_CODE=args[0].trim();
			REQ_DESC=args[1].trim();
			configuration.setDatabaseUrl(args[2].trim());
			configuration.setDatabaseUsername(args[3].trim());
			configuration.setDatabasePassword(args[4].trim());
		} else {
			String msg="None of the expected parameter configuration was identified. "
					+ "The allowed parameter configurations are:"
					+ "\r\n1. No paramter, default configuraiton will be used."
					+ "\r\n2. REQUESTER_CODE, REQUEST_DESCRIPTION."
					+ "\r\n3.  REQUESTER_CODE, REQUEST_DESCRIPTION, DATABASE_URL, DATABASE_USERNAME,DATABASE_PASSWORD";
			
			log.info(msg);
			//check if I need to return 0,1
			throw new DqiException(msg);
		}
		
		log.info("Launching processing");
		if (!DqiRequestContext.checkAndSave(configuration)) {
			log.warn("Context was already save in aprevious step. The new save was ignored");
		}
		Forest runner=new Forest();
		runner.run(REQ_CODE, REQ_MTHD, REQ_DESC);
		log.info("Exit processing.");
	}

}
