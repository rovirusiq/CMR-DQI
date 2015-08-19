package at.eg.sprfrm.cmrdqi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.eg.sprfrm.cmrdqi.dao.TestDqiIssue;
import at.eg.sprfrm.cmrdqi.services.impl.TestDefinitionEnhancer;
import at.eg.sprfrm.cmrdqi.services.impl.TestParseResult;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleParameterParserAdvanced;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleParameterParserBasic;

@RunWith(Suite.class)
@SuiteClasses({ TestDqiIssue.class,TestSimpleParameterParserBasic.class,TestSimpleParameterParserAdvanced.class
	,TestParseResult.class,TestDefinitionEnhancer.class})
public class AllUnitTests {

}
