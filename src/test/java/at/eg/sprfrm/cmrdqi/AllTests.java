package at.eg.sprfrm.cmrdqi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllUnitTests.class,AllIntegrationAdvancedTests.class,
		AllIntegrationBasicTests.class})
public class AllTests {

}
