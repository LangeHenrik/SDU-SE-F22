package dk.sdu.se_f22.sortingmodule.range.rangefilter;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.ReadRangeFilterInterface;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.CreateRangeFilterInterface;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterReader;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

class RangeFilterCreator {
    private CreateRangeFilterInterface dbRangeFilterCreator;
    private ReadRangeFilterInterface dbReader;

    public RangeFilterCreator() {
        this(new DBRangeFilterCreator());
    }

    public RangeFilterCreator(DBRangeFilterCreator dbRangeFilterCreator){
        this.dbRangeFilterCreator = dbRangeFilterCreator;
        try{
            dbRangeFilterCreator.createRangeFilter( "hello object", "uno", "price", 0, 2000);
            dbRangeFilterCreator.createRangeFilter("hello fella", "dos", "height", 0, 4000);
        }catch (InvalidFilterException e){
            System.out.println(e.getMessage());
        }

        dbReader = new DBRangeFilterReader();
    }

    private boolean validateFilter() {
        return true;
    }

    public InternalFilter createInternalFilter(UserInputtedRangeFilter filterCheck) throws InvalidFilterIdException {
        //check if the filter exists in the database
        //if it doesn't, or min, max is invalid
//        return null;
        //if it does and everything is valid:
        DBRangeFilter dbfilter = this.dbReader.getRangeFilter(filterCheck.getId());
        if (validateFilter()) {
            return new InternalFilter(filterCheck.getMin(), filterCheck.getMax(), dbfilter.getProductAttribute());
        } else {
            return null;
        }
    }
}