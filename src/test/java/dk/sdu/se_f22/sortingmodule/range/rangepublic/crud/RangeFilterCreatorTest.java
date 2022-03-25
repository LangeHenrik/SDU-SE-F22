package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class RangeFilterCreatorTest {
    private RangeFilterCRUD rangeFilterCRUD;
    private RangeFilterReader rangeFilterReader;

    @BeforeEach
    void setUp() {
        rangeFilterCRUD = new RangeFilterCRUD();
        rangeFilterReader = new RangeFilterReader();
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
            void validFiltersWithRegularAttributesDouble() {
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                double min = 0;
                double max = 800;

                Assertions.assertDoesNotThrow(
                        () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
                );
            }
        }

        @Test
        @DisplayName("ValidFilters with regular attributes")
            // Maybe later create csv file for testing valid attributes
        void validFiltersWithRegularAttributesLong() {
            String description = "This filter checks a lot of attributes bla bla";
            String name = "Sample filter";
            String productAttribute = "price";
            long min = 0;
            long max = 800;

            Assertions.assertDoesNotThrow(
                    () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
            );
        }

        @Test
        @DisplayName("ValidFilters with regular attributes")
            // Maybe later create csv file for testing valid attributes
        void validFiltersWithRegularAttributesTime() {
            String description = "This filter checks a lot of attributes bla bla";
            String name = "Sample filter";
            String productAttribute = "price";
            Instant min = Instant.ofEpochSecond(0);
            Instant max = Instant.ofEpochSecond(800);

            Assertions.assertDoesNotThrow(
                    () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
            );
        }

        @Nested
        @DisplayName("Invalid filters that should not be created")
        class invalidFiltersThatShouldNotBeCreated {

            @ParameterizedTest(name = "filter description {0}")
            @DisplayName("Empty description")
            @ValueSource(strings = {"", " "})
                //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyDescription(String input) {
                double minDouble = 0;
                double maxDouble = 800;

                long minLong = 0;
                long maxLong = 800;

                Instant minInstant = Instant.ofEpochSecond(0);
                Instant maxInstant = Instant.ofEpochSecond(800);

                Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create(input, "sample name", "Sample attribute", minDouble, maxDouble)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create(input, "sample name", "Sample attribute", minLong, maxLong)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create(input, "sample name", "Sample attribute", minInstant, maxInstant)
                        )
                );

            }


            @ParameterizedTest(name = "filter attribute {0}")
            @DisplayName("Empty product attribute")
            @ValueSource(strings = {"", " "})
                //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyProductAttribute(String input) {
                double minDouble = 0;
                double maxDouble = 800;

                long minLong = 0;
                long maxLong = 800;

                Instant minInstant = Instant.ofEpochSecond(0);
                Instant maxInstant = Instant.ofEpochSecond(800);

                Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", "sample name", input, minDouble, maxDouble)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", "sample name", input, minLong, maxLong)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", "sample name", input, minInstant, maxInstant)
                        )
                );
            }


            @ParameterizedTest(name = "filter name {0}")
            @DisplayName("Empty filter name")
            @ValueSource(strings = {"", " "})
                //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
            void emptyFilterNameV2(String input) {
                double minDouble = 0;
                double maxDouble = 800;

                long minLong = 0;
                long maxLong = 800;

                Instant minInstant = Instant.ofEpochSecond(0);
                Instant maxInstant = Instant.ofEpochSecond(800);

                Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", input, "attribute", minDouble, maxDouble)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", input, "attribute", minLong, maxLong)
                        ),
                        () -> Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD
                                        .create("Testing empty product attribute", input, "attribute", minInstant, maxInstant)
                        )
                );
            }

            @Nested
            @DisplayName("Testing negative min")
            class negativeMin {
                @ParameterizedTest(name = "filter min negative {0}")
                @DisplayName("Negative min double")
                @ValueSource(doubles = {-1.0, -3.67})
                void negativeMinDouble(double doubles) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", doubles, 500)
                    );
                }

                @ParameterizedTest(name = "filter min negative {0}")
                @DisplayName("Negative min long")
                @ValueSource(longs = {-1, -3})
                void negativeMinLong(long longs) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", longs, 500)
                    );
                }

                @ParameterizedTest(name = "filter min negative {0}")
                @DisplayName("Negative min double")
                @ValueSource(ints = {-1, -3})
                void negativeMinTime(int epochSec) {
                    Instant instant = Instant.ofEpochSecond(epochSec); // Format from epochSec. MIGHT BE A BETTER WAY?
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", instant, Instant.ofEpochSecond(500))
                    );
                }
            }

            @Nested
            @DisplayName("Testing negative max")
            class negativeMax {
                @ParameterizedTest(name = "filter max negative {0}")
                @DisplayName("Negative max double")
                @ValueSource(doubles = {-1.0, -3.67})
                void negativeMaxDouble(double doubles) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative Max", "name", "price", 0, doubles)
                    );
                }

                @ParameterizedTest(name = "filter max negative {0}")
                @DisplayName("Negative max long")
                @ValueSource(longs = {-1, -3})
                void negativeMaxLong(long longs) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative Max", "name", "price", 0, longs)
                    );
                }

                @ParameterizedTest(name = "filter max negative {0}")
                @DisplayName("Negative max double")
                @ValueSource(ints = {-1, -3})
                void negativeMaxTime(int epochSec) {
                    Instant instant = Instant.ofEpochSecond(epochSec); // Format from epochSec. MIGHT BE A BETTER WAY?
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative Max", "name", "price", Instant.ofEpochSecond(0), instant)
                    );
                }
            }

            @Nested
            @DisplayName("Testing max less than min")
            class MaxLessThanMin {
                @ParameterizedTest(name = "filter max less than min {0}")
                @DisplayName("Max less than min double")
                @MethodSource("integerProvider")
                    // References the stream of doubles below
                void testMaxLessThanMin(double min, double max) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", min, max)
                    );
                }

                @ParameterizedTest(name = "filter max less than min {0}")
                @DisplayName("Max less than min long")
                @MethodSource("integerProvider")
                    // References the stream of doubles below
                void testMaxLessThanMin(long min, long max) {
                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", min, max)
                    );
                }

                @ParameterizedTest(name = "filter max less than min {0}")
                @DisplayName("Max less than min instant")
                @MethodSource("integerProvider")
                    // References the stream of doubles below
                void testMaxLessThanMin(int min, int max) {
                    Instant minInstant = Instant.ofEpochSecond(min);
                    Instant maxInstant = Instant.ofEpochSecond(max);

                    Assertions.assertThrows(InvalidFilterException.class,
                            () -> rangeFilterCRUD.create("negative min", "name", "price", minInstant, maxInstant)
                    );
                }
            }

            // Provides multiple parameters for the max less than min test
            static Stream<Arguments> integerProvider() {
                return Stream.of(
                        arguments(3, 1),
                        arguments(15, 13)
                );
            }
        }
    }
}