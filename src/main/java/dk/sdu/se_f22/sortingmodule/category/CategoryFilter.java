package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryCRUDInterface;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryCRUD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryFilter implements CategoryFilterInterface {
    CategoryCRUDInterface DBCrud;

    public CategoryFilter(){
        this.DBCrud = new CategoryCRUD();
    }

    public SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs) {
        Collection<Product> newProducts = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        if (categoryIDs.isEmpty()) {
            return searchHits;
        }

        for (Integer categoryID : categoryIDs) {
            Category tmpCategory = DBCrud.getCategoryById(categoryID);
            categories.add(tmpCategory);
        }

        for (Object oldProduct : searchHits.getProducts()) {
            if(oldProduct instanceof Product){
                Product product = (Product)oldProduct;

                for(Category category : categories){
                    if(category == null){
                        break;
                    }
                    if (category.getRequirementFieldName().toLowerCase().equals("category")) {
                        Pattern pattern = Pattern.compile(
                                "(^|[^\\w])\\/?(" + category.getRequirementValue() + ")\\/?([^\\w+]|$)",
                                Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(product.getCategory());
                        boolean matchFound = matcher.find();

                        if (matchFound) {

                            if (!newProducts.contains(product)) {
                                newProducts.add(product);
                            }
                            break;
                        }
                    } else if (category.getRequirementFieldName().toLowerCase().equals("name")) {
                        Pattern pattern = Pattern.compile(
                                "(^|[^\\w])(" + category.getRequirementValue() + ")([^\\w+]|$)",
                                Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(product.getName());
                        boolean matchFound = matcher.find();

                        if (matchFound) {

                            if (!newProducts.contains(product)) {
                                newProducts.add(product);
                            }
                            break;
                        }
                    }
                }
            }
        }
        searchHits.setProducts(newProducts);

        return searchHits;
    }
}
