package at.eg.sprfrm.cmrdqi.services.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSimpleParameterParserBasic implements ISimpleParameterParserMarkers{
	
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(TestSimpleDqiDefinitionExecutor.class);
	
	private SimpleParameterParser prs=new SimpleParameterParser();
	
	private void actualParsingWithException(String definition,char expectedChar,int expectedPosition ) throws DefinitionParseException {
		try {
			prs.parseDefinition(definition);
		} catch (DefinitionParseException ex) {
			String msg="The input["+definition+"] should not have been parsed. "
					+ "Expected an offending char["+expectedChar+"] at position["+expectedPosition+"]";
			String s1=""+expectedChar;
			String s2=""+ex.getOffendingChar();
			assertEquals(msg, s1, s2);
			assertEquals(msg, expectedPosition, ex.getPosition());
			throw ex;
		}
	}

	@Test(expected=DefinitionParseException.class)
	public void testParseException1() {
		String definition="input$\\$";
		actualParsingWithException(definition, '\\', 6);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException2() {
		String definition="i${ceva}abc$$dfg";
		actualParsingWithException(definition, '$', 12);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException3() {
		String definition="i${ceva} a\\bc$$dfg";
		actualParsingWithException(definition, '$', 14);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException4() {
		String definition="${c\\ev\\a}\\ a\\bc\\}dfg}";
		actualParsingWithException(definition, '}', 20);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException5() {
		String definition="i\\np\\ut${abc\\d} altceva\\\\asi inca ceva. Mai departe${$";
		actualParsingWithException(definition, '$', 53);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException6() {
		String definition="}ceva care sa crape la primul record";
		actualParsingWithException(definition, '}', 0);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException7() {
		String definition="ceva un ${parametru\\}alt}";
		actualParsingWithException(definition, '}', 20);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException8() {
		String definition="ceva { dincolo ${param}";
		actualParsingWithException(definition, '{', 5);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException9() {
		String definition="{ceva { dfsfsfsfs$";
		actualParsingWithException(definition, '{', 0);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException10() {
		String definition="un paramtreu ${neconve.ntional} from dual}";
		actualParsingWithException(definition, '.', 22);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException11() {
		String definition="un paramtreu ${neco@nventional} from dual}";
		actualParsingWithException(definition, '@', 19);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException12() {
		String definition="un p$aramtreu ${neco@nventional} from dual}";
		actualParsingWithException(definition, 'a', 5);
	}
	
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException13() {
		String definition="ceva ${alo} from dual\\";
		actualParsingWithException(definition, EMPTY_CHAR, definition.length()-1);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException14() {
		String definition="ceva ${alo_undeva";
		actualParsingWithException(definition, EMPTY_CHAR, definition.length()-1);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException15() {
		String definition="ceva alo_undeva $";
		actualParsingWithException(definition, EMPTY_CHAR, definition.length()-1);
	}
	
	@Test(expected=DefinitionParseException.class)
	public void testParseException16() {
		String definition="ceva alo_undeva ${";
		actualParsingWithException(definition, EMPTY_CHAR, definition.length()-1);
	}
	
	@Test
	public void testParseOK01() {
		String definition="un param@treu ${neconventional} from dual";
		prs.parseDefinition(definition);
	}
	
	@Test
	public void testParseOK02() {
		String definition="un param@tre.u ${neconventional} from dual";
		prs.parseDefinition(definition);
	}
	
	@Test
	public void testParseOK03() {
		String definition="un p_a-ram@tre.u ${necon-venti_onal} from dual";
		prs.parseDefinition(definition);
	}
	
	@Test
	public void testParseOK04() {
		String definition="select ${PARAM1} from ${PARAM2} where ${PARAM3}=${PARAM4}";
		prs.parseDefinition(definition);
	}

	//carcatere invalide in parametru

}
