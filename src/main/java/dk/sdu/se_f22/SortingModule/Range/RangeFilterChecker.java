package dk.sdu.se_f22.SortingModule.Range;

class RangeFilterChecker {
    private static DBRangeFilter getDBRangeFilter(int id) {
        return null;
    }

    private static boolean validateFilter() {
        return false;
    }

    public static InternalFilter check(RangeFilter filterCheck) {
        //check if the filter exists in the database
        //if it doesn't, or min, max is invalid
//        return null;
        //if it does and everything is valid:
        DBRangeFilter dbfilter = getDBRangeFilter(filterCheck.getId());
        if (validateFilter()) {
            return new InternalFilter(filterCheck.getMin(), filterCheck.getMax(), dbfilter.getProductAttribute());
        } else {
            return null;
        }
    }
}