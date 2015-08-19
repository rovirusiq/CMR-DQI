package at.eg.sprfrm.cmrdqi.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class InfrastructureConfig {
	
	/*************************************************
	 * 
	 * INFRASTRUCTURE
	 * 
	 ************************************************/	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		
		DqiRequestContext ctx=DqiRequestContext.get();
		
		BasicDataSource ds=new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl(ctx.getDatabaseUrl());
		ds.setUsername(ctx.getDatabaseUsername());
		ds.setPassword(ctx.getDatabasePassword());
		ds.setDefaultAutoCommit(false);
		return ds;
	}
	
	 @Bean
     public PlatformTransactionManager transactionManager() {
         return new DataSourceTransactionManager(dataSource());
     }
	

}
