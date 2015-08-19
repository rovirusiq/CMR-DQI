package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.services.IDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;
import at.eg.sprfrm.cmrdqi.testutil.TestDqiHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
public class TestDefinitionEnhancerInt {
	
private static final Logger log=LoggerFactory.getLogger(TestSimpleParameterProviderInt.class);
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	@Autowired
	@Qualifier("definitionEnhancer")
	private IDefinitionEnhancer testSubject;
	
	@Autowired
	@Qualifier("testDqiFactoryObject")
	private TestIntegrationFactoryObject factoryObject;

	@Test
	public void testCorrectReplacement() {
		DqiDefinition def=factoryObject.getAsReferenceTestDefintionByIndex(3);//definition with parameter from databse
		log.info("Defintion read from database:"+def.getCheck());
		EnhancedDefinition enhancedDefintion=testSubject.enhanceDefinition(def,EnhancedDefinition.class);
		assertEquals("The original check should be equal",def.getCheck(),enhancedDefintion.getOriginalCheck());
		assertEquals("The enhanced check is not correct","select column_A from a_table where column_B='3.14'",enhancedDefintion.getCheck());
	}
	
	@Test(expected=InvalidDefinitionParameterException.class)
	@SuppressWarnings("unused")
	public void testWithException() {
		DqiDefinition def=factoryObject.getAsReferenceTestDefintionByIndex(4);//definition with parameter from databse
		log.info("Defintion read from database:"+def.getCheck());
		DqiDefinition enhancedDefintion=testSubject.enhanceDefinition(def);
		assertEquals("An exception was exepcted. The exception never came. This is inpolote",true,false);
	}
	
}
