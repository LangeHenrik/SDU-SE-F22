package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.rangefilter.RangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.util.ArrayList;
import java.util.List;

public class PopulateDBFromCsv {
    /** Parses a csv file into a list of DBRangeFilter objects.
     * <br>
     * The first line will be ignored. Assuming these are headers.
     *
     * @param fileName the file is assumed to be located in the range folder of the resources folder
     * @return A list of the {@link RangeFilter}s stored in the csv file
     */
    public static List<RangeFilter> readDBFiltersFromCSV(String fileName){
        List<String> lines = Helpers.readFromCSV(fileName);

        // remove headers from the file
        lines.remove(0);

        List<RangeFilter> out = new ArrayList<>();

        for(String line: lines){
            RangeFilter parsed = ParseSingleLine(line);
            if (parsed == null){
                continue;
            }
            out.add(parsed);
        }

        return out;
    }

    public static RangeFilter ParseSingleLine(String line) {
        String[] lineSplit = line.split(",");
        RangeFilter filter;
        try{
            filter = RangeFilterCreator.createAndCheckFilter(lineSplit[0], lineSplit[1], lineSplit[2], Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4]));
            return filter;
        }catch (InvalidFilterException e){
            System.out.println("you fucked up, invalid filter" + e.getMessage());
        }

        return null;
    }

}
