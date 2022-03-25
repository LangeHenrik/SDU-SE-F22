package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DoubleFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.fail;

public class RangeFilterCRUDTest {

    private RangeFilterCRUD rangeFilterCRUD;

    @BeforeAll
    public void setup () {
        rangeFilterCRUD = new RangeFilterCRUD();
    }

    @Nested
    class CRUDDeleterTest  {
        @Test
        @DisplayName("Delete valid doubleFilter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void deleteValidDoubleFilter(int id, String name, String description, String productAttribute, double min, double max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }
            RangeFilter rangeFilter = new DoubleFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (InvalidFilterIdException e) {
                fail("Fail because id did not exist");
            }
        }
    }

}
