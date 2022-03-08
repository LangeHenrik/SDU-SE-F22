package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;

import java.util.ArrayList;
import java.util.List;

public class PopulateDBFromCsv {
    /** Parses a csv file into a list of DBRangeFilter objects.
     * <br>
     * The first line will be ignored. Assuming these are headers.
     *
     * @param fileName the file is assumed to be located in the range folder of the resources folder
     * @return
     */
    static List<DBRangeFilter> readDBFiltersFromCSV(String fileName){
        List<String> lines = Helpers.readFromCSV(fileName);

        // remove headers from the file
        lines.remove(0);

        List<DBRangeFilter> out = new ArrayList<>();

        for(String line: lines){
            DBRangeFilter parsed = ParseSingleLine(line);
            if (parsed == null){
                continue;
            }
            out.add(parsed);
        }

        return out;
    }

    static DBRangeFilter ParseSingleLine(String line) {
        String[] lineSplit = line.split(",");
        DBRangeFilter filter;
        try{
            filter = DBRangeFilterCreator.createAndCheckFilter(lineSplit[0], lineSplit[1], lineSplit[2], Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4]));
            return filter;
        }catch (InvalidFilterException e){
            System.out.println("you fucked up, invalid filter" + e.getMessage());
        }

        return null;
    }

}
