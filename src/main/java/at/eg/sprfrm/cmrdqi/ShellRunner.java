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
	
	
	private static final String HELP_MESSAGE="The allowed parameter configurations are:"
			+ "\r\n1. CHK_AREA, CHK_GROUP,CHK_SUBGROUP."
			+ "\r\n2. CHK_AREA, CHK_GROUP,CHK_SUBGROUP, REQUESTER_CODE, REQUEST_DESCRIPTION."
			+ "\r\n3. CHK_AREA, CHK_GROUP,CHK_SUBGROUP, REQUESTER_CODE, REQUEST_DESCRIPTION, DATABASE_URL, DATABASE_USERNAME,DATABASE_PASSWORD";
	


	public static void main(String[] args) {
		
		log.info("Checkin parameters");
		DqiConfiguration configuration=new DqiConfiguration();
		configuration.readConfigurationFromFiles();
		
		
		String REQ_CODE=DEFAULT_REQUESTER_CODE;
		String REQ_MTHD=DEFAULT_REQUESTER_METHOD;
		String REQ_DESC=DEFAULT_REQUESTER_DESCRIPTION;
		
		
		String area="";
		String group="";
		String subgroup="";
		
		
		
		if ( (args.length==1) && ("HELP".equals((args[0]!=null)? args[0].toUpperCase():null)) ){
			log.info(HELP_MESSAGE);
		} else if (args.length==3) {
			log.info("3 paramters provided. They will be read as CHK_AREA, CHK_GROUP,CHK_SUBGROUP");
			area=args[0].trim().toUpperCase();
			group=args[1].trim().toUpperCase();
			subgroup=args[2].trim().toUpperCase();
			log.info("CHK_AREA="+area);
			log.info("CHK_GROUP="+group);
			log.info("CHK_SUBGROUP="+subgroup);
		} else if (args.length==5) {
			log.info("5 paramters provided. They will be read as CHK_AREA, CHK_GROUP,CHK_SUBGROUP, REQUESTER_CODE, REQUEST_DESCRIPTION");
			area=args[0].trim().toUpperCase();
			group=args[1].trim().toUpperCase();
			subgroup=args[2].trim().toUpperCase();
			REQ_CODE=args[3].trim().toUpperCase();
			REQ_DESC=args[4].trim();
			log.info("CHK_AREA="+area);
			log.info("CHK_GROUP="+group);
			log.info("CHK_SUBGROUP="+subgroup);
			log.info("REQUESTER_CODE="+REQ_CODE);
			log.info("REQUEST_DESCRIPTION="+REQ_DESC);
		} else if (args.length==8){
			log.info("8 paramters provided. They will be read as CHK_AREA, CHK_GROUP,CHK_SUBGROUP, REQUESTER_CODE, REQUEST_DESCRIPTION, DATABASE_URL, DATABASE_USERNAME,DATABASE_PASSWORD");
			area=args[0].trim().toUpperCase();
			group=args[1].trim().toUpperCase();
			subgroup=args[2].trim().toUpperCase();
			REQ_CODE=args[3].trim().toUpperCase();
			REQ_DESC=args[4].trim();
			configuration.setDatabaseUrl(args[5].trim());
			configuration.setDatabaseUsername(args[6].trim());
			configuration.setDatabasePassword(args[7].trim());
			log.info("CHK_AREA="+area);
			log.info("CHK_GROUP="+group);
			log.info("CHK_SUBGROUP="+subgroup);
			log.info("REQUESTER_CODE="+REQ_CODE);
			log.info("REQUEST_DESCRIPTION="+REQ_DESC);
			log.info("DATABASE_URL="+args[5].trim());
			log.info("DATABASE_USERNAME="+args[6].trim());
			log.info("DATABASE_PASSWORD="+args[7].trim());
			
		} else {
			String msg="None of the expected parameter configuration was identified.";
			log.info(msg);
			log.info(HELP_MESSAGE);
			//check if I need to return 0,1
			throw new DqiException(msg);
		}
		
		log.info("Launching processing");
		if (!DqiRequestContext.checkAndSave(configuration)) {
			log.warn("Context was already save in aprevious step. The new save was ignored");
		}
		Forest runner=new Forest();
		runner.run(area,group,subgroup,REQ_CODE, REQ_MTHD, REQ_DESC);
		log.info("Exit processing.");
	}

}
