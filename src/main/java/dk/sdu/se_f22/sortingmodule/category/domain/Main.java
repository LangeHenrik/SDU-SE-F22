package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilterInterface;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        CategoryCRUDInterface categoryCRUD = new CategoryCRUD();
        CategoryFilterInterface categoryFilter = new CategoryFilter();

        List<Product> products = new ArrayList<Product>();
        products.add(new Product(UUID.randomUUID(), 3.571, new ArrayList<String>(), 918019744, 1250.75, Instant.MAX, Instant.MIN, "Components/Processors", "Intel Xeon X3320 processor 2.5 GHz 6 MB L2", "Intel Xeon X3320, Intel\u00ae Xeon\u00ae, LGA 775 (Socket T), 45 nm, Intel, X3320, 2.5 GHz Intel Xeon X3320. Processor family: Intel\u00ae Xeon\u00ae, Processor socket: LGA 775 (Socket T), Processor lithography: 45 nm. Market segment: Server, Number of Processing Die Transistors: 456 M, Processing Die size: 164 mm\u00b2. Package type: Retail box. Images Type Map: <div><img src=\"https://ark.intel.com/inc/images/diagrams/diagram-5.gif\" title=\"Block Diagram\"..."));
        products.add(new Product(UUID.randomUUID(), 4.446, new ArrayList<String>(), 205464709, 1787.50, Instant.MAX, Instant.MIN, "PC/Laptops", "Lenovo ThinkPad T410 35.8 cm (14.1)", "Lenovo ThinkPad T410, 35.8 cm (14.1\"), 1280 x 800 pixels Lenovo ThinkPad T410. Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels"));
        products.add(new Product(UUID.randomUUID(), 4.834, new ArrayList<String>(), 460691622, 1775.50, Instant.MAX, Instant.MIN, "PC/Laptops", "Lenovo IdeaPad S300", "Lenovo IdeaPad S300 Lenovo IdeaPad S300"));
        products.add(new Product(UUID.randomUUID(), 3.601, new ArrayList<String>(), 804210574, 1400.25, Instant.MAX, Instant.MIN, "Components/Storage/SSDs", "Lenovo 45N8296-RFB internal solid state drive 128 GB", "Lenovo 45N8296-RFB, 128 GB Lenovo 45N8296-RFB. SSD capacity: 128 GB"));

        SearchHits demoSearchHit = new SearchHits();
        demoSearchHit.setProducts(products);

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(6);
        ids.add(7);

        System.out.println("All products before filtering method:");

        for (Product foundProduct : demoSearchHit.getProducts()) {
            System.out.println(foundProduct.getCategory() + " - " + foundProduct.getName());
        }

        System.out.println("\nFiltering for categories 'Storage' and 'Processors' \n");

        SearchHits filteredSearchHit = categoryFilter.filterProductsByCategory(demoSearchHit, ids);

        System.out.println("Products found with filtering method:");

        for (Product foundProduct : filteredSearchHit.getProducts()) {
            System.out.println(foundProduct.getCategory() + " - " + foundProduct.getName());
        }
    }
}
