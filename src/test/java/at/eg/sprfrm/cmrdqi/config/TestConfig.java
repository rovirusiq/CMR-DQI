package at.eg.sprfrm.cmrdqi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TestConfig  extends TestServicesConfig{

/************************************************************************************************************
 *
 * Beans Declarations
 *
 ************************************************************************************************************/	
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource()); 
	}


}
