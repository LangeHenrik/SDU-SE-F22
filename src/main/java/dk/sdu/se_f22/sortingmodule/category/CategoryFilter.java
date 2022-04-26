package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryFilter implements CategoryFilterInterface {

    public SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs) {
        Collection<CategoryProduct> newProducts = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        if(categoryIDs.isEmpty()){
            return searchHits;
        }

        for (Integer categoryID : categoryIDs) {
            Category tmpCategory = CategoryDBConnection.shared.getCategoryById(categoryID);
            categories.add(tmpCategory);
        }

        for (Object oldProduct : searchHits.getProducts()) {
            if(oldProduct instanceof CategoryProduct){
                CategoryProduct product = (CategoryProduct)oldProduct;
                String[] fieldNameArray;

                for(Category category : categories){
                    if (category.getRequirementFieldName().toLowerCase().equals("category")) {
                        Pattern pattern = Pattern.compile("(^|[^\\w])\\/?(" + category.getRequirementValue() + ")\\/?([^\\w+]|$)", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(product.getCategory());
                        boolean matchFound = matcher.find();

                        if (matchFound) {
                            System.out.println("Match found: " + product.getName());

                            if (!newProducts.contains(product)) {
                                newProducts.add(product);
                            }
                            break;
                        } else {
                            System.out.println("Match not found:" + product.getName());
                        }
                    } else if (category.getRequirementFieldName().toLowerCase().equals("name")) {
                        Pattern pattern = Pattern.compile(category.getRequirementValue());
                        Matcher matcher = pattern.matcher(product.getName());
                        boolean matchFound = matcher.matches();

                        if (matchFound) {
                            System.out.println("Match found: " + product.getName());

                            if (!newProducts.contains(product)) {
                                newProducts.add(product);
                            }
                            break;
                        } else {
                            System.out.println("Match not found:" + product.getName());
                        }
                    } else if (category.getRequirementFieldName().toLowerCase().equals("instock")) {
                        if (category.getRequirementValue() != null) {
                            for (String fieldInStock : product.getInStock()) {
                                Pattern pattern = Pattern.compile(category.getRequirementValue());
                                Matcher matcher = pattern.matcher(fieldInStock);
                                boolean matchFound = matcher.matches();

                                if (matchFound) {
                                    System.out.println("Match found: " + product.getName());

                                    newProducts.add(product);
                                    break;
                                } else {
                                    System.out.println("Match not found:" + product.getName());
                                }
                            }
                        } else {
                            if (product.getInStock().isEmpty()) {
                                System.out.println("Match found: " + product.getName());

                                newProducts.add(product);
                                break;
                            } else {
                                System.out.println("Match not found: " + product.getName());
                            }
                        }
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
