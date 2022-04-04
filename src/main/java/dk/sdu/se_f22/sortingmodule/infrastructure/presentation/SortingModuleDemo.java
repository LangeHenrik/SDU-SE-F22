package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;

public class SortingModuleDemo {
    public static void main (String[] args) {
        SortingModuleImpl module = new SortingModuleImpl();

        // TODO: Wait for search module infrastructure to finish there "queryIndexOfType" and
        //  "search" metode, before using below:
        module.setSearchString("Hello, World!");
        module.addCategory(1);
        module.addCategory(3);
        module.addCategory(5);
        module.addRange(2, 2.46854, 3.89);
        module.search();
    }
}
