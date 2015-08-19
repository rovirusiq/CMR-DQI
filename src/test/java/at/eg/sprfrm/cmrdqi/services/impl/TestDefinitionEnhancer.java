package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;
import at.eg.sprfrm.cmrdqi.model.TestUnitFactoryObject;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import org.junit.Before;
import org.junit.Test;

public class TestDefinitionEnhancer {
	
	private DefinitionEnhancer testSubject;
	private TestUnitFactoryObject factoryObject;
	
	@Before
	public void before() {
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("PARAM1", "ALT");
		paramProvider.addParameter("PARAM2", "is");
		paramProvider.addParameter("PARAM3", "day");
		paramProvider.addParameter("PARAM4", "But");
		
		testSubject=new DefinitionEnhancer(paramProvider);
		
		factoryObject=new TestUnitFactoryObject();
		
	}
	
	@Test
	public void testEnhanceDefinition() {
		DqiDefinition def=factoryObject.createDefinition("${PARAM4} it ${PARAM2} not that ${PARAM3}");
		EnhancedDefinition enhancedDefinition=testSubject.enhanceDefinition(def,EnhancedDefinition.class);

		assertEquals("Orginal check check has failed",def.getCheck(),enhancedDefinition.getOriginalCheck());
		assertEquals("Enhanced check has failed","But it is not that day",enhancedDefinition.getCheck());
		
	}
	
	@Test(expected=InvalidDefinitionParameterException.class)
	
	public void testEnhanceDefinitionWithException() {
		DqiDefinition def=factoryObject.createDefinition("${PARAM4} it ${PARAM2} not that ${PARAMXXX}");
		@SuppressWarnings("unused")
		DqiDefinition enhancedDefinition=testSubject.enhanceDefinition(def);
		assertEquals("An exception was expected. The exception was not generated",true,false);
		
	}

	@Test
	public void testGenerateEnhancedDefinition() {
		DqiDefinition def=factoryObject.createDefinition("ceva si cu ${PARAM1} rezulta altceva");
		String enhancedDefintion=testSubject.generateEnhancedDefinition(def);
		
		assertEquals("ceva si cu ALT rezulta altceva",enhancedDefintion);
	}
	
	@Test(expected=InvalidDefinitionParameterException.class)
	public void testGenerateEnhancedDefinitionWithException() {
		DqiDefinition def=factoryObject.createDefinition("ceva si cu ${PARAMXXX} rezulta altceva");
		@SuppressWarnings("all")
		String enhancedDefintion=testSubject.generateEnhancedDefinition(def);
		
		assertEquals("An exception was expected. The exception was not generated",true,false);
	}
}
