package at.eg.sprfrm.cmrdqi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.eg.sprfrm.cmrdqi.dao.TestDqiDefinitionBasicInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiExecutionBasicInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiRequestAdvancedInt;
import at.eg.sprfrm.cmrdqi.dao.TestDqiRequestBasicInt;

@RunWith(Suite.class)
@SuiteClasses({ TestDqiDefinitionBasicInt.class, TestDqiRequestBasicInt.class,
		TestDqiExecutionBasicInt.class, TestDqiRequestAdvancedInt.class })
public class AllIntegrationBasicTests {

}
