package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortingModuleImplTest {

    @Test
    void searchTest() {
        SearchModuleImpl s = new SearchModuleImpl();

        assertNotNull(s.search("Hello World!"));
    }
}
