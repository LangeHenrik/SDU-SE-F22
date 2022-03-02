package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingData;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingModule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSearchModule {

    @Test
    public void testQueryIndexOfType() {
        // Create a mock indexing module with mock data
        MockIndexingModule indexingModule = new MockIndexingModule();
        indexingModule.indexingData.add(new MockIndexingData("Hello"));
        indexingModule.indexingData.add(new MockIndexingData("Foo"));
        indexingModule.indexingData.add(new MockIndexingData("Bar"));
        indexingModule.indexingData.add(new MockIndexingData("Foo"));


        SearchModuleImpl searchModule = new SearchModuleImpl();
        searchModule.addIndexingModule(indexingModule);

        // Query the indexing module
        var helloQuery = searchModule.queryIndexOfType(MockIndexingData.class, List.of("Hello"));
        var fooQuery = searchModule.queryIndexOfType(MockIndexingData.class, List.of("Foo"));

        // Test querying of data
        assertArrayEquals(
                helloQuery.toArray(),
                List.of(new MockIndexingData("Hello")).toArray()
        );

        assertArrayEquals(
                fooQuery.toArray(),
                List.of(new MockIndexingData("Foo"), new MockIndexingData("Foo")).toArray()
        );

        // Test throwing
        assertThrows(
                NoSuchElementException.class,
                () -> searchModule.queryIndexOfType(String.class, List.of(""))
        );

        // Test removal
        searchModule.removeIndexingModule(indexingModule);
        assertThrows(
                NoSuchElementException.class,
                () -> searchModule.queryIndexOfType(MockIndexingData.class, List.of(""))
        );
    }
}
