package at.eg.sprfrm.cmrdqi.config;

public class AbstractDqiPropertiesProvider implements IDqiPropertiesProvider {
	
	protected String databaseUrl;
	protected String databaseUsername;
	protected String databasePassword;
	
	
	public AbstractDqiPropertiesProvider() {
		
	}
	
	protected void populateFrom(IDqiPropertiesProvider properties) {
		this.databaseUrl=properties.getDatabaseUrl();
		this.databaseUsername=properties.getDatabaseUsername();
		this.databasePassword=properties.getDatabasePassword();
	}
	

	@Override
	public String getDatabaseUrl() {
		return this.databaseUrl; 
	}

	@Override
	public String getDatabaseUsername() {
		return this.databaseUsername;
	}

	@Override
	public String getDatabasePassword() {
		return this.databasePassword; 
	}

}
