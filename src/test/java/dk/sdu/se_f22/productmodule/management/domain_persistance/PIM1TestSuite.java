package dk.sdu.se_f22.productmodule.management.domain_persistance;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ProductManagerTest.class, ProductAttributeTest.class, ProductJSONReaderTest.class, BaseProductTest.class})
public class PIM1TestSuite {
    
}