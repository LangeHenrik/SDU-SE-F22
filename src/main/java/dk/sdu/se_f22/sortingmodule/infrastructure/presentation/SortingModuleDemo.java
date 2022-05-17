package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;

public class SortingModuleDemo {
    public static void main (String[] args) {
        SortingModuleImpl module = new SortingModuleImpl();

        // "search" method, before using below:
        module.setSearchString("Hello, World!");
        module.addCategory(1);
        module.addCategory(3);
        module.addCategory(5);
        module.addRangeDouble(4, 2.46854, 3.89);
        module.setPagination(0, 5);
        SearchHits data = module.search();

        // Data received
        System.out.println("Products: " + data.getProducts().toString());
        System.out.println("Brands: " + data.getBrands().toString());
        System.out.println("Content: " + data.getContents().toString());

        // TODO maybe change when range module is
        //  updated, otherwise just uncomment when needed.
        // System.out.println(module.getAvailableRangeFilters());
    }
}
