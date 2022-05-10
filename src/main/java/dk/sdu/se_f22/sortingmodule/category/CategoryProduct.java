package dk.sdu.se_f22.sortingmodule.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryProduct {
    private String name;
    private String category;
    private List<String> inStock;

    public CategoryProduct(String name, String category, ArrayList<String> inStock) {
        this.name = name;
        this.category = category;
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getInStock() {
        return inStock;
    }
}
