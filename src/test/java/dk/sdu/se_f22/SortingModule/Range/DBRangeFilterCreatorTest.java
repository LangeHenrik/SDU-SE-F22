package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.fail;

class DBRangeFilterCreatorTest {
    private DBRangeFilterCreator dbRangeFilterCreator;

    @BeforeEach
    void setUp() {
        dbRangeFilterCreator = new DBRangeFilterCreator();
    }

    @Nested
    @DisplayName("Create Db filters")
    class createDbFilters {
        @Nested
        @DisplayName("Valid filters that should pass")
        class validFiltersThatShouldPass {
            @Test
            @DisplayName("ValidFilters with regular attributes")
            // Maybe later create csv file for testing valid attributes
            void validFiltersWithRegularAttributes() {
                int id = 0;
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                double min = 0;
                double max = 800;

                AtomicInteger returnId = new AtomicInteger(); // Refactoring needed
                Assertions.assertDoesNotThrow(
                        () -> returnId.set(dbRangeFilterCreator.createRangeFilter(id, description, name, productAttribute, min, max))
                );


                DBRangeFilter createdFilter = dbRangeFilterCreator.getRangeFilterFromDB(returnId.get());

                Assertions.assertAll("Testing that the values created match the values supplied",
                        () -> Assertions.assertEquals(id, createdFilter.getId()),
                        () -> Assertions.assertEquals(description, createdFilter.getDescription()),
                        () -> Assertions.assertEquals(productAttribute, createdFilter.getProductAttribute()),
                        () -> Assertions.assertEquals(name, createdFilter.getName()),
                        () -> Assertions.assertEquals(min, createdFilter.getMin()),
                        () -> Assertions.assertEquals(max, createdFilter.getMax())
                );
            }
        }

        @Nested
        @DisplayName("Invalid filters that should not be created")
        class invalidFiltersThatShouldNotBeCreated {
            @ParameterizedTest(name = "filter description {0}")
            @DisplayName("Empty description")
            @ValueSource(strings = {"", " "})
            @CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyDescription(String input) {
                dbRangeFilterCreator = new DBRangeFilterCreator();

                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator
                                .createRangeFilter(0,input, "sample name", "Sample attribute",0,400)
                );
            }


            @ParameterizedTest(name = "filter attribute {0}")
            @DisplayName("Empty product attribute")
            @ValueSource(strings = {"", " "})
            @CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyProductAttribute(String input) {
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator
                        .createRangeFilter(0,"Testing empty product attribute", "sample name", input,0,400)
                );
            }


            @ParameterizedTest(name = "filter name {0}")
            @DisplayName("Empty filter name")
            @ValueSource(strings = {"", " "})
            @CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyFilterNameV2(String input) {
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator.createRangeFilter(0,"unit testing", input, "price", 0, 400)
                );
            }
        }
    }



    @Test
    void getRangeFilterFromDB() {
        fail("not yet implemented");
    }

}