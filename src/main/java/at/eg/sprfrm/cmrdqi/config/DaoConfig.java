package at.eg.sprfrm.cmrdqi.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiExecutionDao;
import at.eg.sprfrm.cmrdqi.dao.IDqiRequestDao;

@Configuration
@MapperScan("at.eg.sprfrm.cmrdqi.dao")
public class DaoConfig extends InfrastructureConfig{


/************************************************************************************************************
 *
 * Declared Beans
 *
 ************************************************************************************************************/	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean fact=new SqlSessionFactoryBean();
		fact.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		fact.setTypeAliasesPackage("at.eg.sprfrm.cmrdqi.model");
		fact.setDataSource(dataSource());
		return fact.getObject();
	}
/************************************************************************************************************
 *
 * Injected beans
 *

 ************************************************************************************************************/
		@Autowired
		@Qualifier("dqiRequestDao")
		public IDqiRequestDao requestDao;
		
		@Autowired
		@Qualifier("dqiExecutionDao")
		public IDqiExecutionDao executionDao;
		
		@Autowired
		@Qualifier("dqiDefinitionDao")
		public IDqiDefinitionDao definitionDao;		
	
}
