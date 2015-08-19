package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestParseResult {
	
	private static Logger log=LoggerFactory.getLogger(TestParseResult.class);
	
	private void checkParameters(ParseResult parseResult,String...paramNames) {
		List<String> pN=parseResult.getParameterNames();
		log.info("ParamList:"+pN);
		for (String str : paramNames) {
			assertTrue("The list of parameters should contain ["+str+"]",pN.contains(str));
		}
	}
	
	

	@Test
	public void testHasParametersTrue1() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.PARAMETER, "text2");
		lstBuilder.add(ParserNodeType.TEXT, "text3");
		lstBuilder.add(ParserNodeType.TEXT, "text4");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertTrue("The list contains parameters. The result of the method call hasParameters should have been true",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersTrue2() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "text1");
		lstBuilder.add(ParserNodeType.TEXT, "text2");
		lstBuilder.add(ParserNodeType.TEXT, "text3");
		lstBuilder.add(ParserNodeType.TEXT, "text4");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertTrue("The list contains parameters. The result of the method call hasParameters should have been true",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersTrue3() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.TEXT, "text2");
		lstBuilder.add(ParserNodeType.TEXT, "text3");
		lstBuilder.add(ParserNodeType.PARAMETER, "text4");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertTrue("The list contains parameters. The result of the method call hasParameters should have been true",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersTrue4() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "text4");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertTrue("The list contains parameters. The result of the method call hasParameters should have been true",pr.hasParameters());
	}
	
	
	@Test
	public void testHasParametersFalse1() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		//empty list
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertFalse("The list contains NO parameters. The result of the method call hasParameters should have been FALSE",pr.hasParameters());
	}
	
	
	@Test
	public void testHasParametersFalse2() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertFalse("The list contains NO parameters. The result of the method call hasParameters should have been FALSE",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersFalse3() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.TEXT, "text2");
		lstBuilder.add(ParserNodeType.TEXT, "text3");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertFalse("The list contains NO parameters. The result of the method call hasParameters should have been FALSE",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersFalse4() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.TEXT, "text2");
		lstBuilder.add(ParserNodeType.TEXT, "text3");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		assertFalse("The list contains NO parameters. The result of the method call hasParameters should have been FALSE",pr.hasParameters());
	}
	
	@Test
	public void testHasParametersFalseWithNullList() {
		ParseResult pr=new ParseResult("not important for the test", null);
		assertFalse("The list contains NO parameters. The result of the method call hasParameters should have been FALSE",pr.hasParameters());
	}

	@Test
	public void testGetParameterNamesOk1() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param2");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1","param2");
	}
	
	@Test
	public void testGetParameterNamesOk2() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param2");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1","param2");
	}
	
	@Test
	public void testGetParameterNamesOk3() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param2");
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1","param2");
	}
	
	@Test
	public void testGetParameterNamesOk4() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1");
	}
	
	@Test
	public void testGetParameterNamesOk5() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "text1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1");
	}
	
	@Test
	public void testGetParameterNamesOk6() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr,"param1");
	}
	
	@Test
	public void testGetParameterNamesOk7() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		//empty list
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		checkParameters(pr);
	}
	
	@Test
	public void testGetParameterNamesOk8() {
		ParseResult pr=new ParseResult("not important for the test", null/*null List*/);
		checkParameters(pr);
	}
	
	@Test(expected=AssertionError.class)
	public void testGetParameterNamesNok() {
		ParseResult pr=new ParseResult("not important for the test", null/*null List*/);
		checkParameters(pr,"something1");
	}

	@Test
	public void testGetActualDefinitionOkWithoutParameters() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "Hello");
		lstBuilder.add(ParserNodeType.TEXT, " World!");
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", "Test");
		
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		
		String rsp=pr.getActualDefinition(paramProvider);
		
		assertEquals("Hello World!",rsp);
		
	}
	
	@Test
	public void testGetActualDefinitionOkWithParameters1() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.TEXT, "Hello");
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", " World!");
		
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		
		String rsp=pr.getActualDefinition(paramProvider);
		
		assertEquals("Hello World!",rsp);
		
	}
	
	@Test
	public void testGetActualDefinitionOkWithParameters2() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.TEXT, " World!");
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", "Hello");
		
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		
		String rsp=pr.getActualDefinition(paramProvider);
		
		assertEquals("Hello World!",rsp);
		
	}
	
	@Test
	public void testGetActualDefinitionOkWithParameters3() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", "Hello!");
		
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		
		String rsp=pr.getActualDefinition(paramProvider);
		
		assertEquals("Hello!",rsp);
		
	}
	
	@Test(expected=InvalidDefinitionParameterException.class)
	public void testGetActualDefinitionWithException() {
		MockParserNodeListBuilder lstBuilder=new MockParserNodeListBuilder();
		lstBuilder.add(ParserNodeType.PARAMETER, "param1");
		lstBuilder.add(ParserNodeType.PARAMETER, "param2");
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", "Hello!");
		
		ParseResult pr=new ParseResult("not important for the test", lstBuilder.build());
		
		String rsp=pr.getActualDefinition(paramProvider);
		
		assertEquals("Hello!",rsp);
		
	}

}
