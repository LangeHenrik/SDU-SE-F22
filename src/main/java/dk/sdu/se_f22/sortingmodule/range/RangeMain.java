package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.*;


import java.util.ArrayList;
import java.util.List;

/**
 * 1. Filter(id, min, max) bliver sendt fra SOM-1
 * 2. Vi bruger InternalFilter til at matche Filter og DBRangeFilter
 * 2.1 InternalFilter(Filter, productAttribute)
 * 3. Internal filter bruges til at validere på
 **/

public class RangeMain {

    public static void main(String[] args) {
        Product product = new Product();
        product.set(ProductAttribute.ID, "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f4e");
        product.set(ProductAttribute.AVERAGE_USER_REVIEW, "4.446");
        product.set(ProductAttribute.EAN, "2054647099864");
        product.set(ProductAttribute.PRICE, "1787.50");
        product.set(ProductAttribute.PUBLISHED_DATE, "2021-06-02T05:05:06.622164");
        product.set(ProductAttribute.EXPIRATION_DATE, "2025-01-25T07:40:33.169509");
        product.set(ProductAttribute.CATEGORY, "PC/Laptops");
        product.set(ProductAttribute.NAME, "Lenovo ThinkPad T410 35.8 cm (14.1\")");
        product.set(ProductAttribute.DESCRIPTION, "Lenovo ThinkPad T410, 35.8 cm (14.1\"), 1280 x 800 pixels Lenovo ThinkPad T410. Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels");
        product.set(ProductAttribute.WEIGHT, "1");

        ArrayList<String> locations = new ArrayList<>();
        locations.add("Charlottenlund");
        locations.add("Herning");
        product.setLocations(locations);

        ProductHit productHit = new ProductHit(product);
    }

    private static void sortingShowcase1() {
        RangeFilterCRUDInterface crud = new RangeFilterCRUD();

        // The web ui should get all filters to see what filters they want to use
        List<RangeFilter> rangeFilters = crud.readAll();

        // let's see the information about the filters: (not strictly necessary)
        for (RangeFilter filter : rangeFilters) {
            System.out.println(filter.getId() + " " + filter.getName() + " " + filter.getType());
            if (filter.getType() == FilterTypes.DOUBLE) {
                try {
                    System.out.println("printing double value: min: " + filter.getDbMinDouble() + " max: " + filter.getDbMaxDouble());
                } catch (InvalidFilterTypeException e) {
                    //This should not happen since we have just checked that the type matches
                    e.printStackTrace();
                }
            }
        }

        // Now we assume that the user has decided to activate the filters that are located at index 0 and 1 in the filter list
        List<RangeFilter> filtersToUse = new ArrayList<>();
        filtersToUse.add(rangeFilters.get(0));
        filtersToUse.add(rangeFilters.get(1));

        // The user has applied these settings:
        // the first filter is a double filter, so the inputs are doubles
        try {
            filtersToUse.get(0).setUserMax(3.0); // userMax must be less than DBmax
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        try {
            filtersToUse.get(0).setUserMin(2.0); // min must be less than max
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        // the second filter is a long filter
        try {
            filtersToUse.get(1).setUserMax(3); // userMax must be less than DBmax
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        try {
            filtersToUse.get(1).setUserMin(2); // min must be less than max
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }


        // Now lets use the filters to filter a search result
        // This is when filtersToUse is passed on from the ui to the infrastructure group
        SearchHits searchHits = new SearchHits();

        try {
            searchHits = RangeFilterFilterResults.filterResults(searchHits, filtersToUse);
        } catch (IllegalImplementationException e) {
            // This will happen if you implement RangeFilter interface on your own, and pass instances of this instead of the instances you receive from this module
            e.printStackTrace();
        }

        // the searchHits object now contains a filtered productlist where each filter has been applied
    }


    private static void sortingShowcase2() {
        // this example shows a usecase where the web ui, has a specific list of filter ids, that it always shows
        RangeFilterCRUDInterface crud = new RangeFilterCRUD();

        // The web ui should get all filters to see what filters they want to use
        List<Integer> filterIds = new ArrayList<>();
        //we assume the list of filters used are number 0 and number 1
        filterIds.add(0);
        filterIds.add(1);

        // Now we assume that the user has decided to activate the filters that are located at index 0 and 1 in the filter list
        List<RangeFilter> filtersToUse = new ArrayList<>();
        try {
            for (Integer id : filterIds) {
                filtersToUse.add(crud.read(id));
            }
        } catch (IdNotFoundException e) {
            // This exception happens if the filter id does not exist
            e.printStackTrace();
        } catch (UnknownFilterTypeException e) {
            // this exception happens in the freak case that a new type of filter have been created in the database
            // but has not been implemented in this code
            e.printStackTrace();
        }


        // From here on out the process is the same as above

        // The user has applied these settings:
        // the first filter is a double filter, so the inputs are doubles
        try{
            filtersToUse.get(0).setUserMax(3.0); // userMax must be less than DBmax
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        try {
            filtersToUse.get(0).setUserMin(2.0); // min must be less than max
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        // the second filter is a long filter
        try {
            filtersToUse.get(1).setUserMax(3); // userMax must be less than DBmax
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }

        try {
            filtersToUse.get(1).setUserMin(2); // min must be less than max
        } catch (InvalidFilterTypeException e) {
            e.printStackTrace();
        }


        // Now lets use the filters to filter a search result
        // This is when filtersToUse is passed on from the ui to the infrastructure group
        SearchHits searchHits = new SearchHits();

        try {
            searchHits = RangeFilterFilterResults.filterResults(searchHits, filtersToUse);
        } catch (IllegalImplementationException e) {
            // This will happen if you implement RangeFilter interface on your own, and pass instances of this instead of the instances you receive from this module
            e.printStackTrace();
        }

        // the searchHits object now contains a filtered productlist where each filter has been applied
    }
}
