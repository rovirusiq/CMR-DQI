package at.eg.sprfrm.cmrdqi;

import at.eg.sprfrm.cmrdqi.dao.TestDqiDefinitionBasicInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiExecutionBasicInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiRequestAdvancedInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiRequestBasicInt;
import at.eg.sprfrm.cmrdqi.services.impl.TestDefinitionEnhancerInt;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleParameterParserBasic;
import at.eg.sprfrm.cmrdqi.services.impl.TestSimpleParameterProviderInt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDqiDefinitionBasicInt.class, TestDqiRequestBasicInt.class,
		TestDqiExecutionBasicInt.class, TestDqiRequestAdvancedInt.class, TestSimpleParameterParserBasic.class 
		,TestSimpleParameterProviderInt.class,TestDefinitionEnhancerInt.class})
public class AllIntegrationBasicTests {

}
