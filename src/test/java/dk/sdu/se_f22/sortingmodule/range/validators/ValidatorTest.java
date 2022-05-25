package dk.sdu.se_f22.sortingmodule.range.validators;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ValidatorTest {

    @ParameterizedTest(name = "{0}")
    // the 0 and 0 is for the index in the csv input,
    // if using three or more values in one row add more brackets
    @DisplayName("Testing the NoNegativeValue")
    @ValueSource(doubles = {-1, -2, -3, -Double.MIN_VALUE, -Double.MAX_VALUE})
    void TestingNoNegativeNumber(double input) {
        assertThrows(InvalidFilterException.class,
                () -> Validator.NoNegativeValue(input)
        );
    }

    @ParameterizedTest(name = "{0}")
    // the 0 and 0 is for the index in the csv input,
    // if using three or more values in one row add more brackets
    @DisplayName("No special characters argument")
    @CsvFileSource(resources = "emptyStrings.csv")
    void noSpecialCharactersArgument(String input) {
        // Remember to parse the string inputs to the types you expect them to be
        // If you added more fields per line of CSV remember to add them as params to the method
        assertThrows(InvalidFilterException.class,
                () -> Validator.NoSpecialCharacters(input)
        );
    }

    // Provides multiple parameters for the max less than min test
    static Stream<Arguments> doublesProvider() {
        return Stream.of(
                arguments(1.0, 0.5),
                arguments(15.7,15.6)
        );
    }

    @ParameterizedTest(name = "Min {0} Max {1}")
    // the 0 and 0 is for the index in the csv input, 
    // if using three or more values in one row add more brackets
    @DisplayName("Max less than min throws exception")
    @MethodSource("doublesProvider") // References the stream of doubles below
        // e.g. "1:2", "2:4" where ':' is the delimiter
    void maxLessThanMin(double min, double max) {
        // Remember to parse the string inputs to the types you expect them to be
        // If you added more fields per line of CSV remember to add them as params to the method
        assertThrows(InvalidFilterException.class,
                () -> Validator.MaxLessThanMin(min, max)
        );

    }

}