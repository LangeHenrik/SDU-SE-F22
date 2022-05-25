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
    @DisplayName("Testing that NoNegativeValue throws exception when the value is negative")
    @ValueSource(doubles = {-1, -2, -3, -Double.MIN_VALUE, -Double.MAX_VALUE})
    void TestingNoNegativeNumber(double input) {
        assertThrows(InvalidFilterException.class,
                () -> Validator.NoNegativeValue(input)
        );
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("No special characters validator should throw exception with special characters")
    @CsvFileSource(resources = "emptyStrings.csv")
    void noSpecialCharactersArgument(String input) {
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
    @DisplayName("Max less than min throws exception when max is less than min")
    @MethodSource("doublesProvider") // References the stream of doubles below
    void maxLessThanMin(double min, double max) {
        assertThrows(InvalidFilterException.class,
                () -> Validator.MaxLessThanMin(min, max)
        );

    }

}