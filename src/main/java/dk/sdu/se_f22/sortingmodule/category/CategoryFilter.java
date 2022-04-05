package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryFilter implements CategoryFilterInterface {

    public static SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs) {
        Collection<ProductHit> newProducts = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        if(categoryIDs.isEmpty()){
            return searchHits;
        }

        for (Integer categoryID : categoryIDs) {
            Category tmpCategory = CategoryDBConnection.shared.getCategoryById(categoryID);
            categories.add(tmpCategory);
        }

        for (Object oldProduct : searchHits.getProducts()) {
            if(oldProduct instanceof ProductHit){
                ProductHit product = (ProductHit)oldProduct;

                for(Category category : categories){
                    System.out.println(category.getRequirementStatus());
                    System.out.println(category.getRequirementValue());
                    System.out.println(category.getName());
                    if (category.getRequirementStatus().toLowerCase().equals("contains")) {
                        if(product.getCategory().contains(category.getRequirementValue())) {
                            newProducts.add(product);
                            break;
                        }
                    } else if (category.getRequirementStatus().toLowerCase().equals("in stock")) {
                        System.out.println("VIRKER");
                        if(product.getInStock().contains(category.getRequirementValue())){
                            newProducts.add(product);
                            break;
                        } else if(product.getInStock().isEmpty() != true) {
                            newProducts.add(product);
                            break;
                        }
                    } else {
                        System.out.println("Requirement Status: " + category.getRequirementStatus() + " isn't supported yet");
                    }
                }
            } else {
                System.out.println("The instance " + oldProduct.getClass() + " isn't supported");
            }
        }
        searchHits.setProducts(newProducts);

        return searchHits;
    }
}
