package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.rangefilter.RangeFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Helpers {
    public static List<String> readFromCSV(String fileName) {
        List<String> out = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/test/resources/dk/sdu/se_f22/SortingModule/Range/" + fileName))) {
            while(scanner.hasNextLine()){
                out.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;
    }

    public static List<RangeSearchResultMock> readMockResultsFromFile(String fileName){
        List<String> products = Helpers.readFromCSV(fileName);

        String[] attributeNames = products.get(0).split(",");

        return createMockResultsFromStringList(products, attributeNames);
    }

    public static List<RangeSearchResultMock> createMockResultsFromStringList(List<String> products, String[] attributeNames){
        List<RangeSearchResultMock> mockResults = new ArrayList<>();

        for (String product : products) {
            Map<String, Double> attributeMap = createAttributeMapForMockResults(attributeNames, product);
            if (attributeMap.equals(new HashMap<String, Double>())) continue;

            mockResults.add(new RangeSearchResultMock(attributeMap));
        }

        return mockResults;
    }

    public static Map<String, Double> createAttributeMapForMockResults(String[] attributeNames, String product) {
        String[] productSplit = product.split(",");
        Map<String, Double> attributeMap = new HashMap<>();

        //avoid trying to create products with the attribute names
        if (Objects.equals(attributeNames[0], productSplit[0])) {
            return attributeMap;
        }


        for (int i = 0; i < productSplit.length; i++) {
            attributeMap.put(attributeNames[i], Double.parseDouble(productSplit[i]));
        }
        return attributeMap;
    }

    public static List<RangeFilter> readRangeFiltersFromFile(String fileName){
        List<RangeFilter> mockResults = new ArrayList<>();

        List<String> filters = Helpers.readFromCSV(fileName);

        String[] attributeNames = filters.get(0).split(",");


        for (String filter : filters) {
            String[] filterSplit = filter.split(",");
            if (Objects.equals(attributeNames[0], filterSplit[0])) {
                continue;
            }

            mockResults.add(new RangeFilter(Integer.parseInt(filterSplit[0]), Double.parseDouble(filterSplit[1]), Double.parseDouble(filterSplit[2])));
        }

        return mockResults;
    }

    public static String formatArrays(Object[] expected, Object[] actual){
        return "\nexpected\n" + Arrays.toString(expected) + "\n" +
                "actual:\n" + Arrays.toString(actual) + "\n";
    }
}
