package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterReader;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DBRangeFilterCreatorTest {
    private DBRangeFilterCreator dbRangeFilterCreator;
    private RangeFilterReader dbRangeFilterReader;

    @BeforeEach
    void setUp() {
        dbRangeFilterCreator = new DBRangeFilterCreator();
        dbRangeFilterReader = new RangeFilterReader();
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
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                double min = 0;
                double max = 800;

                Assertions.assertDoesNotThrow(
                        () -> dbRangeFilterCreator.createRangeFilter(description, name, productAttribute, min, max)
                );
            }
        }

        @Nested
        @DisplayName("Invalid filters that should not be created")
        class invalidFiltersThatShouldNotBeCreated {

            @ParameterizedTest(name = "filter description {0}")
            @DisplayName("Empty description")
            @ValueSource(strings = {"", " "})
            //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyDescription(String input) {
                dbRangeFilterCreator = new DBRangeFilterCreator();

                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator
                                .createRangeFilter(input, "sample name", "Sample attribute",0,400)
                );
            }


            @ParameterizedTest(name = "filter attribute {0}")
            @DisplayName("Empty product attribute")
            @ValueSource(strings = {"", " "})
            //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyProductAttribute(String input) {
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator
                        .createRangeFilter("Testing empty product attribute", "sample name", input,0,400)
                );
            }


            @ParameterizedTest(name = "filter name {0}")
            @DisplayName("Empty filter name")
            @ValueSource(strings = {"", " "})
            //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyFilterNameV2(String input) {
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator.createRangeFilter("unit testing", input, "price", 0, 400)
                );
            }

            @ParameterizedTest(name = "filter min negative {0}")
            @DisplayName("Negative min")
            @ValueSource(doubles = {-1.0,-3.67})
            void negativeMin(double doubles){
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator.createRangeFilter("negative min", "name","price",doubles,500)
                );
            }

            @ParameterizedTest(name = "filter max negative {0}")
            @DisplayName("Negative max")
            @ValueSource(doubles = {-1.0,-3.67})
            void negativeMax(double doubles){
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator.createRangeFilter("negative min", "name","price", 0,doubles)
                );
            }


            @ParameterizedTest(name = "filter max less than min {0}")
            @DisplayName("Max less than min")
            @MethodSource("doublesProvider") // References the stream of doubles below
            void testMaxLessThanMin(double min, double max){
                Assertions.assertThrows(InvalidFilterException.class,
                        () -> dbRangeFilterCreator.createRangeFilter("negative min", "name","price",min,max)
                );
            }

            // Provides multiple parameters for the max less than min test
            static Stream<Arguments> doublesProvider() {
                return Stream.of(
                        arguments(1.0, 0.5),
                        arguments(15.7,15.6)
                );
            }

        }
    }

    @Test
    void getRangeFilterFromDB() {
        String description = "This filter checks a lot of attributes bla bla";
        String name = "Sample filter";
        String productAttribute = "price";
        double min = 0;
        double max = 800;

        AtomicInteger returnId = new AtomicInteger(); // Refactoring needed
        Assertions.assertDoesNotThrow(
                () -> {
                    DBRangeFilter createdFilter  = dbRangeFilterCreator.createRangeFilter(description, name, productAttribute, min, max);
                    Assertions.assertAll("Testing that the values created match the values supplied",
                            () -> Assertions.assertEquals(description, createdFilter.getDescription()),
                            () -> Assertions.assertEquals(productAttribute, createdFilter.getProductAttribute()),
                            () -> Assertions.assertEquals(name, createdFilter.getName()),
                            () -> Assertions.assertEquals(min, createdFilter.getMin()),
                            () -> Assertions.assertEquals(max, createdFilter.getMax())
                    );
                }
            );
    }

    @Test
    void getRangeFilterFromId() {
        String description = "This filter checks a lot of attributes bla bla";
        String name = "Sample filter";
        String productAttribute = "price";
        double min = 0;
        double max = 800;
        final int[] id = new int[1];

        Assertions.assertDoesNotThrow(
                () -> id[0] = dbRangeFilterCreator.createRangeFilter(description, name, productAttribute, min, max).getId()
        );

        DBRangeFilter retrievedFilter = null;
        try {
            retrievedFilter = dbRangeFilterReader.getRangeFilter(id[0]);
        } catch (InvalidFilterIdException e) {
            fail("Id does not exist");
        }

        Assertions.assertEquals(id[0], retrievedFilter.getId());
    }

}