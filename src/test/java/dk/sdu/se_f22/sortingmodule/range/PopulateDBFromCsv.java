package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.rangepublic.DoubleFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
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
    public static List<RangeFilter> readFiltersFromCSV(String fileName){
        List<String> lines = Helpers.readFromCSV(fileName);

        // remove headers from the file
        lines.remove(0);

        List<RangeFilter> out = new ArrayList<>();

        for(String line: lines){
            out.add(ParseSingleLine(line));
        }

        return out;
    }

    /** Currently only works for Double filters
     *
     * @param line a single line of csv, which contains the necessary information to create a Double filter
     * @return the filter created from the information in the line
     */
    public static RangeFilter ParseSingleLine(String line) {
        String[] lineSplit = line.split(",");
        RangeFilter filter;

        filter = new DoubleFilter(Integer.parseInt(lineSplit[0]), lineSplit[1], lineSplit[2], lineSplit[3], Double.parseDouble(lineSplit[4]), Double.parseDouble(lineSplit[5]));
        return filter;
    }

}
