package at.eg.sprfrm.cmrdqi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.eg.sprfrm.cmrdqi.DqiException;

public class DqiConfiguration extends AbstractDqiPropertiesProvider implements IDqiPropertiesProvider{
	
	private static final String CMT_CHECK_PROPERTIES = "cmt_check.properties";
	private static final Logger log=LoggerFactory.getLogger(DqiConfiguration.class);
		
	/**
	 * @param databaseUrl the databaseUrl to set
	 */
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	/**
	 * @param databaseUsername the databaseUsername to set
	 */
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	/**
	 * @param databasePassword the databasePassword to set
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}


	private Properties readPropertiesFileFromClassPath(String fileName) throws DqiException {
		
		Properties props=new Properties();
		log.info("Reading file:"+fileName);
		try(InputStream st=
				Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)){
			if (st==null) throw new DqiException("File["+fileName+"] cannot be found on classpath");
			props.load(st);
		
		} catch (IOException e) {
			throw new DqiException("The properties file could not be closed",e);
		}
		log.info("Finished readinf file:"+fileName);
		return props;
	}
	
	
	public void readConfigurationFromFiles() throws DqiException{
		
		log.info("Reading values for configuration files");
		Properties dbProps=this.readPropertiesFileFromClassPath(CMT_CHECK_PROPERTIES);
		this.setDatabaseUrl(dbProps.getProperty("database.url"));
		this.setDatabaseUsername(dbProps.getProperty("database.username"));
		this.setDatabasePassword(dbProps.getProperty("database.password"));
		log.info("Finishe Reading the configuration from file");
		
	}

}
