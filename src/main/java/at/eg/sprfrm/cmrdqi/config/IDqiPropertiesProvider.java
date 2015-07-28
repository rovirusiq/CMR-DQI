package at.eg.sprfrm.cmrdqi.config;

public interface IDqiPropertiesProvider {
	
	public abstract String getDatabaseUrl();
	public abstract String getDatabaseUsername();
	public abstract String getDatabasePassword();

}
