package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.category.CategoryProduct;

import java.util.ArrayList;
import java.util.Collection;

public class TemporaryConnectionTest {

    public static void main(String[] args) {
        //testMethod();
        //System.out.println(CategoryDBConnection.shared.getAllCategories());
        //System.out.println(CategoryDBConnection.shared.getCategoryById(1));
        //System.out.println(CategoryDBConnection.shared.getCategoryById(4));
        //System.out.println(CategoryDBConnection.shared.getCategoryById(8));

        // USED FOR OWN GOODS AND TESTING
        /*
        CategoryDBConnection.shared.createCategory("Storageeee", "Beskrivelse", "Storageeee", 3);
        CategoryDBConnection.shared.createCategory("Laptopeee", "Beskrivelse", "Laptopeee", 1, 2);
        //CategoryDBConnection.createCategory("PC", "Beskrivelse", "PC", 2, 2);

        List<Category> categoryList = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList) {
            System.out.println(category);
        }

        CategoryDBConnection.shared.updateName(1, "Storagee");
        CategoryDBConnection.shared.updateDescription(2, "Beskrivelse 2");
        CategoryDBConnection.shared.updateParentID(3,1);

        System.out.println("AFTER UPDATE");
        List<Category> categoryList2 = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList2) {
            System.out.println(category);
        }

        System.out.println("AFTER DELETE");
        CategoryDBConnection.shared.deleteCategory(3);
        List<Category> categoryList3 = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList3) {
            System.out.println(category);
        }
         */

        /*
        CategoryDBConnection.shared.createCategory("Components", "Beskrivelse", "Components", 2);
        CategoryDBConnection.shared.createCategory("Processors", "Beskrivelse", "Processors", 1, 2);
        CategoryDBConnection.shared.createCategory("Storage", "Beskrivelse", "Storage",1, 2);
        CategoryDBConnection.shared.createCategory("SSDs", "Beskrivelse", "SSDs",3, 2);

        CategoryDBConnection.shared.createCategory("In Stock", "Beskrivelse", "NOT NULL", 3);
        CategoryDBConnection.shared.createCategory("Arhus", "Beskrivelse", "Arhus",5, 3);
        CategoryDBConnection.shared.createCategory("Vejle", "Beskrivelse", "Vejle", 5, 3);
        CategoryDBConnection.shared.createCategory("Silkeborg", "Beskrivelse", "Silkeborg", 5, 3);
         */

        // CategoryDBConnection.shared.createCategory("Lenovo", "Beskrivelse", "Lenovo", 1);

        testMethod();

        /*
        String category = "Components/Processors/Storage/SSDs";
        String notCategory = "fsaafa/dada/dad";
        List<String> inStock = new ArrayList<>();
        List<String> inStock2 = new ArrayList<>();
        List<String> notInStock = new ArrayList<>();
        inStock.add("Arhus");
        inStock.add("Vejle");
        inStock.add("Silkeborg");
        inStock2.add("heajf");

        CategoryProductTestFU productTest = new CategoryProductTestFU(notCategory, inStock, "1");
        CategoryProductTestFU productTest2 = new CategoryProductTestFU(category, notInStock, "2");
        CategoryProductTestFU productTest3 = new CategoryProductTestFU(category, inStock, "3");
        CategoryProductTestFU productTest4 = new CategoryProductTestFU("sda", inStock2, "4");
        CategoryProductTestFU productTest7 = new CategoryProductTestFU(notCategory, notInStock, "5");

        Collection<CategoryProductTestFU> col = new ArrayList<>();

        col.add(productTest);
        col.add(productTest2);
        col.add(productTest3);
        col.add(productTest4);
        col.add(productTest7);
        List<Integer> skrid = new ArrayList<>();
        skrid.add(1);
        skrid.add(2);
        skrid.add(3);
        skrid.add(4);
        skrid.add(5);
        skrid.add(6);
        skrid.add(7);
        skrid.add(8);

        SearchHits sh = new SearchHits();

        sh.setProducts(col);

        CategoryFilter filter = new CategoryFilter();
        SearchHits result = CategoryFilter.filterProductsByCategory(sh,skrid);

        for(Object oldProduct : result.getProducts()){
            if(oldProduct instanceof CategoryProductTestFU){
                CategoryProductTestFU product = (CategoryProductTestFU)oldProduct;
                System.out.println(product.getName());
            }
        }
         */
    }

    public static void testMethod() {
        CategoryFilter categoryFilter = new CategoryFilter();
        CategoryDBConnection categoryDB = new CategoryDBConnection();

        ArrayList<String> liste = new ArrayList<String>();
        liste.add("Vejle");
        ArrayList<String> lorteListe = new ArrayList<String>();
        lorteListe.add("Tis");

        Collection products = new ArrayList<CategoryProduct>();
        products.add(new CategoryProduct("Lenovo Intel hard drive", "Components/Storage/Harddrives", new ArrayList<>()));
        products.add(new CategoryProduct("Lenovo Intel hard drive 2", "Monitors", new ArrayList<>()));
        products.add(new CategoryProduct("Lenovo Intel hard drive 3", "Components/Storage/Harddrives", liste));
        products.add(new CategoryProduct("fuck dig", "skrid", lorteListe));

        SearchHits testSearchHit = new SearchHits();
        testSearchHit.setProducts(products);

        ArrayList<Integer> testCategoryIDs = new ArrayList<Integer>();
        testCategoryIDs.add(1);
        testCategoryIDs.add(2);
        testCategoryIDs.add(9);

        categoryFilter.filterProductsByCategory(testSearchHit, testCategoryIDs);

        for (Object addedProduct : testSearchHit.getProducts()) {
            if (addedProduct instanceof CategoryProduct) {
                System.out.println(((CategoryProduct)addedProduct).getName());
            }
        }
    }
}
