package dk.sdu.se_f22.productmodule.management;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectClasses({ProductManagerTest.class, ProductAttributeTest.class, JSONReaderTest.class, ProductTest.class})
@SelectPackages({"dk.sdu.se_f22.productmodule.management"})
public class TestSuite {

}