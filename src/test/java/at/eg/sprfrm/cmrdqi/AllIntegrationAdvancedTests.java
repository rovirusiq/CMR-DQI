package at.eg.sprfrm.cmrdqi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.eg.sprfrm.cmrdqi.dao.TestDqiDefinitionAdvancedInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiExecutionAdvancedInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiIssueInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiRequestAdvancedInt;
import at.eg.sprfrm.cmrdqi.services.impl.TestDqiPersistenceService;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleDqiDefinitionExecutor;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleParameterParserAdvanced;

@RunWith(Suite.class)
@SuiteClasses({ TestDqiDefinitionAdvancedInt.class,TestDqiRequestAdvancedInt.class
	,TestDqiExecutionAdvancedInt.class,TestDqiIssueInt.class
	,TestDqiPersistenceService.class,TestSimpleDqiDefinitionExecutor.class
	,TestSimpleParameterParserAdvanced.class})

public class AllIntegrationAdvancedTests {

}
