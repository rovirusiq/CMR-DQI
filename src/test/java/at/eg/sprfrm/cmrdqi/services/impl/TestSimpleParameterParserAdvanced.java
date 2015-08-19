package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSimpleParameterParserAdvanced {
	
	

	private static final Logger log=LoggerFactory.getLogger(TestSimpleDqiDefinitionExecutor.class);
	
	private SimpleParameterParser prs=new SimpleParameterParser();
	
	
	private void checkNumberAndTypesOfNodes(List<ParserNode> toCheck,int totalNumberOdNodes,Integer... indexesForParameterNodes) {
		assertEquals("The size of the list is not the one exepcted.",totalNumberOdNodes,toCheck.size());	
		for (Integer index : indexesForParameterNodes) {
			ParserNodeType t=toCheck.get(index).getType();
			assertTrue("The Type for the Parser Node at index["+index+"] should be ["+ParserNodeType.PARAMETER.value()+"]",(t==ParserNodeType.PARAMETER));
		}
	}
	
	private void checkContentOfNodes(List<ParserNode> toCheck,String...content) {
		assertEquals("The size of the list shoud have the same size "
				+ "as the content parameter",toCheck.size(),content.length);
		
		for (int i = 0; i < content.length; i++) {
			String listContent=toCheck.get(i).getContent();
			assertEquals("The contnet of the Node at index["+i+"] does not match the expected content",content[i],listContent);
		}
	}
	

	@Test
	public void testOneParameterInTheMiddle() {
		String originalDefinition="select ${ceva} from dual";
		List<ParserNode> lst=prs.processStream(originalDefinition);
		log.info("#Result List:"+lst);
		checkNumberAndTypesOfNodes(lst, 3, 1);
		checkContentOfNodes(lst, "select ","ceva"," from dual");
	}
	
	@Test
	public void testOneParameterAtTheBegining() {
		String originalDefinition="${ceva} se intampla in lumea asta";
		List<ParserNode> lst=prs.processStream(originalDefinition);
		log.info("#Result List:"+lst);
		checkNumberAndTypesOfNodes(lst, 2, 0);
		checkContentOfNodes(lst, "ceva"," se intampla in lumea asta");
	}
	
	@Test
	public void testOneParameterAtTheEnd() {
		String originalDefinition="In lumea asta se intampla ${ceva}";
		List<ParserNode> lst=prs.processStream(originalDefinition);
		log.info("#Result List:"+lst);
		checkNumberAndTypesOfNodes(lst, 2, 1);
		checkContentOfNodes(lst, "In lumea asta se intampla ","ceva");
	}
	
	@Test
	public void testMoreParameters1() {
		String originalDefinition="In ${lu-me1a} asta ${s_e} intampla ${2ce3va}";
		List<ParserNode> lst=prs.processStream(originalDefinition);
		log.info("#Result List:"+lst);
		checkNumberAndTypesOfNodes(lst, 6, 1,3,5);
		checkContentOfNodes(lst, "In ","lu-me1a"," asta ","s_e"," intampla ","2ce3va");
	}
	
	@Test
	public void testMoreParametersActualDefinition1() {
		String originalDefinition="You will never walk alone";
		ParseResult pr=prs.parseDefinition(originalDefinition);
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param1", "Hello!");
		
		assertEquals("You will never walk alone",pr.getActualDefinition(paramProvider));
	}
	
	@Test
	public void testMoreParametersActualDefinition2() {
		String originalDefinition="${pa_ram_3} ${param1} never walk ${param2}";
		ParseResult pr=prs.parseDefinition(originalDefinition);
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		paramProvider.addParameter("param2", "alone");
		paramProvider.addParameter("param1", "will");
		paramProvider.addParameter("pa_ram_3", "You");
		
		assertEquals("You will never walk alone",pr.getActualDefinition(paramProvider));
	}
	
	
	@Test(expected=InvalidDefinitionParameterException.class)
	public void testMoreParametersActualDefinitionWithException() {
		String originalDefinition="You will never ${non_existent_paramater} alone";
		ParseResult pr=prs.parseDefinition(originalDefinition);
		
		MockParameterProvider paramProvider=new MockParameterProvider();
		
		assertEquals("You will never walk alone",pr.getActualDefinition(paramProvider));
	}
	
}
