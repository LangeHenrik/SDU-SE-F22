package dk.sdu.se_f22.productmodule.index;


// Requires ProductHit from group 4.4 and works it gets approved and merged

import java.util.*;

public class ProductIndex implements IProductIndex{

    private int categoryHits = 0;
    private int nameHits = 0;
    private int descriptionHits = 0;
    private List<ProductHit> sortedList = new ArrayList<>();

    // Method for finding amount of hits within a product by a token, then returning an indexed list by the hits
    public List<ProductHit> indexProductsByToken(List<ProductHit> products, List<String> token) {


        for (int i = 0; i < products.size(); i++) {
            for (int n = 0; n < token.size(); n++) {
                String[] categoryWords = products.get(i).getCategory().toLowerCase().split("/");
                String[] nameWords = products.get(i).getName().toLowerCase().split(" ");
                String[] descriptionWords = products.get(i).getDescription().toLowerCase().split(" ");


                for (String cateElem : categoryWords) {
                    if (cateElem.contains(token.get(n).toLowerCase())) {
                        categoryHits += 1;
                    }
                }
                for (String nameElem : nameWords) {
                    if (nameElem.contains(token.get(n).toLowerCase())) {
                        nameHits += 1;
                    }
                }
                for (String descElem : descriptionWords) {
                    if (descElem.contains(token.get(n).toLowerCase())) {
                        descriptionHits += 1;
                    }
                }
            }
            int total = categoryHits + nameHits + descriptionHits;
            /*
            System.out.printf("Product: " + i +
                            "\nCategory hit counter: %4d " +
                            "\nName hit counter: %8d " +
                            "\nDescription hit counter: %d " +
                            "\nTotal: %19d\n\n",
                            categoryHits, nameHits, descriptionHits, total);
            */
            products.get(i).setHitNum(total);
            sortedList.add(products.get(i));
            Collections.sort(sortedList);


            categoryHits = 0;
            nameHits = 0;
            descriptionHits = 0;
        }
        return sortedList;
    }
}