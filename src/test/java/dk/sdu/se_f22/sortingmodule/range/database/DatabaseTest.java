package dk.sdu.se_f22.sortingmodule.range.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void create() {

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
        // expect a time filter with the following attributes
            // name: "test name time"
            // description: "test description"
            // productAttribute: "expirationDate"
            // min: toBeDecided
            // max: tbd

        // read with id 3
            // the filter is written in the seed script
        // expect a long filter with the following attributes
            // name: "test name long"
            // description: "test description"
            // productAttribute: "ean"
            // min: 0
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