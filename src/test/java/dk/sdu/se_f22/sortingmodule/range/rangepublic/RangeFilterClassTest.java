package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeFilterClassTest {
    // TODO write test class for Time filter

    @Test
    @Disabled
    void testEquals() {
        // This test should be split up into many tests to test each step/level of the equals method

        // Here follows an overview of the necessary tests:

        // For non-matching tests, all other attributes should be equal

        // Non-matching filters:
        // A filter and an instance of an ordinary Object should not equal
        // two filters with different ids, should not equal
        // two filters with different names should not equal
        // two filters with different descriptions should not equal
        // two filters looking at different product attributes

        // In specific classes:
        // Different filter types with same attributes
        // DBMin differs
        // DBMax differs
        // UserMin differs
        // USerMax differs


        // Matching filters:
        // Maybe we should create an implementation,
        // which inherits from RangeFilterClass to test if equals works,
        // if the two filters match

        // Specific filter classes
        // Test each type for matching filters
    }
}