package dk.sdu.se_f22.sortingmodule.range.database;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;
    @BeforeEach
    void setup(){
        database = new Database();
    }


    @Test
    void create() {

    }

    @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
    // the 0 and 0 is for the index in the csv input,
    // if using three or more values in one row add more brackets
    @DisplayName("Test read from range filter database")
    @CsvFileSource (resources = "DatabaseFilters.csv", numLinesToSkip = 1)
    void testReadFromRangeFilterDatabase(String id, String name, String description,String productAttribute, String min,String max) {


       int filterID =Integer.parseInt(id);
       double filterMin = Double.parseDouble(min);
       double filterMax = Double.parseDouble(max);
        DBRangeFilter dbTestFilter = database.read(filterID);
        Assertions.assertAll(
                () -> assertEquals(filterID, dbTestFilter.getId()),
                () -> assertEquals(name, dbTestFilter.getName()),
                () -> assertEquals(description, dbTestFilter.getDescription()),
                () -> assertEquals(productAttribute, dbTestFilter.getProductAttribute()),
                () -> assertEquals(filterMin, dbTestFilter.getMin()),
                () -> assertEquals(filterMax, dbTestFilter.getMax())
        );
    }


    @ParameterizedTest (name = "input: {0} ")
    @DisplayName("Test read of invalid ID from Database")
    @ValueSource(ints = {-1,1000,Integer.MIN_VALUE,Integer.MAX_VALUE})
    void testReadOfInvalidIdFromDatabase(int inputId) {
        assertThrows(InvalidFilterIdException.class,
                () -> database.read(inputId)
        );

    }


    @Test
    void read() {
        //first 3 expected succesful tests
        //then 2 or more tests that should fail

        // read with id 1
            // the filter is written in the seed script
        // expect a double filter with the following attributes
            // name: "test name double"
            // description: "test description"
            // productAttribute: "price"
            // min: 0
            // max: 10

        // read with id 2
            // the filter is written in the seed script
        // expect a long filter with the following attributes
            // name: "test name long"
            // description: "test description"
            // productAttribute: "ean"
            // min: 0
            // max: tbd


        // read with id 3
            // the filter is written in the seed script
        // expect a time filter with the following attributes
            // name: "test name time"
            // description: "test description"
            // productAttribute: "expirationDate"
            // min: toBeDecided
            // max: tbd


        //tests of invalid ids
        // read with id -1
            // the id is negative which does not make sense
        // expect an exception along the lines of InvalidFilterId

        // read with id 100
            // hopefully there does not exist 100 ids in the database and thus it should return an InvalidFilterIdException
            // this test should possibly be written in a way that reads the amount of ids in the database and tries with that number +1

        // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }

    @Test
    void readAllFilters() {

    }
}