package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dk.sdu.se_f22.sortingmodule.range.rangepublic.LongFilterTest.getTestFilter;
import static org.junit.jupiter.api.Assertions.*;

public class GetterTests {
    @Nested
    @DisplayName("Getters on DoubleFilters")
    class gettersOnDoubleFilters {
        @Test
        @DisplayName("Get as long")
        void getAsLong() {
            DoubleFilter doubleFilter = DoubleFilterTest.getTestFilter("price");

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getDbMaxLong),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getDbMinLong),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getUserMaxLong),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getUserMinLong)
            );
        }

        @Test
        @DisplayName("Get as Time")
        void getAsTime() {
            DoubleFilter doubleFilter = DoubleFilterTest.getTestFilter("price");

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getDbMaxInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getDbMinInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getUserMaxInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, doubleFilter::getUserMinInstant)
            );
        }
    }

    @Nested
    @DisplayName("Getters on LongFilters")
    class gettersOnLongFilters {
        @Test
        @DisplayName("get As Time")
        void getAsTime() {
            LongFilter longFilter = getTestFilter();

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getDbMaxInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getDbMinInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getUserMaxInstant),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getUserMinInstant)
            );
        }

        @Test
        @DisplayName("get as Double")
        void getAsDouble() {
            LongFilter longFilter = getTestFilter();

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getDbMaxDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getDbMinDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getUserMaxDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, longFilter::getUserMinDouble)
            );
        }
    }


    @Nested
    @DisplayName("Getters on TimeFilters")
    class gettersOnTimeFilters {

        @Test
        @DisplayName("Get as Long")
        void getAsLong() {
            TimeFilter timeFilter = TimeFilterTest.getTestFilter("publishedDate");

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getDbMaxLong),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getDbMinLong),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getUserMaxLong),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getUserMinLong)
            );
        }

        @Test
        @DisplayName("get as Double")
        void getAsDouble() {
            TimeFilter timeFilter = TimeFilterTest.getTestFilter("publishedDate");

            assertAll(
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getDbMaxDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getDbMinDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getUserMaxDouble),
                    () -> assertThrows(InvalidFilterTypeException.class, timeFilter::getUserMinDouble)
            );
        }
    }
}
