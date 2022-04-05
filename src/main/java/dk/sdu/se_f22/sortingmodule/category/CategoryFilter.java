package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CategoryFilter implements CategoryFilterInterface {

    public SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs) {
        SearchHits newHits = new SearchHits();
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

                for(Category suhaib : categories){
                    System.out.println(suhaib.getRequirementStatus());
                    System.out.println(suhaib.getRequirementValue());
                    System.out.println(suhaib.getName());
                    if (suhaib.getRequirementStatus().toLowerCase().equals("contains")) {
                        if(product.getCategory().contains(suhaib.getRequirementValue())) {
                            newProducts.add(product);
                            break;
                        }
                    } else if (suhaib.getRequirementStatus().toLowerCase().equals("in stock")) {
                        System.out.println("VIRKER");
                        if(product.getInStock().contains(suhaib.getRequirementValue())){
                            newProducts.add(product);
                            break;
                        } else if(product.getInStock().isEmpty() != true) {
                            newProducts.add(product);
                            break;
                        }
                    } else {
                        System.out.println("Requirement Status: " + suhaib.getRequirementStatus() + " isn't supported yet");
                    }
                }
            } else {
                System.out.println("The instance " + oldProduct.getClass() + " isn't supported");
            }
        }
        newHits.setProducts(newProducts);

        return newHits;
    }
}
