package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.services.IDqiPersistenceService;
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
public class TestSimpleParameterProviderInt {
	
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(TestSimpleParameterProviderInt.class);
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcT;
	
	
	@Autowired
	@Qualifier("testHelper")
	private TestDqiHelper testHelper;
	
	@Autowired
	@Qualifier("persistenceService")
	private IDqiPersistenceService persistenceService;
	

	@Test
	public void testExistingValue() {
		SimpleParameterProvider testSubject=new SimpleParameterProvider(persistenceService);
		String expectedValue=testHelper.selectValueForParameter();
		String actualValue=testSubject.getValue(TestDqiHelper.PARAMETER_NAME_1);
		assertEquals("Parameter Value should be the same.",expectedValue,actualValue);
	}
	
	@Test
	public void testExistingValueWithLowerCase() {
		SimpleParameterProvider testSubject=new SimpleParameterProvider(persistenceService);
		String expectedValue=testHelper.selectValueForParameter();
		String actualValue=testSubject.getValue(TestDqiHelper.PARAMETER_NAME_1.toLowerCase());
		assertEquals("Parameter Value should be the same.",expectedValue,actualValue);
	}
	
	@Test(expected=InvalidDefinitionParameterException.class)
	@SuppressWarnings("all")
	public void testNonExistingValue() {
		SimpleParameterProvider testSubject=new SimpleParameterProvider(persistenceService);
		String actualValue=testSubject.getValue("BLA_BLA_SOMETHING");
	}
	
	
	
}
