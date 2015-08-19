package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.config.TestConfig;
import at.eg.sprfrm.cmrdqi.model.TestIntegrationFactoryObject;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class,loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
public class TestSimpleParameterProvider {
	
	@Autowired
	private TestIntegrationFactoryObject factoryObject;
	
	private SimpleParameterProvider srcParam;
	
	@Before
	public void setUp() {
		MockParameterDefinitionDao.Builder builder=new MockParameterDefinitionDao.Builder();
		builder.add(factoryObject.createDefinitionParameter("param1", "value1"));
		builder.add(factoryObject.createDefinitionParameter("param2", "value2"));
		MockParameterDefinitionDao definitionParameterDao=builder.build();
		srcParam=new SimpleParameterProvider(new MockPersistenceService(definitionParameterDao));
	}

	@Test
	public void testAcquireParameterList() {
		assertEquals("value1",srcParam.getValue("param1"));
		assertEquals("value2",srcParam.getValue("param2"));
	}
	
	
	@Test(expected=InvalidDefinitionParameterException.class)
	public void testAcquireParameterListWithException() {
		srcParam.getValue("param3");
	}
}
