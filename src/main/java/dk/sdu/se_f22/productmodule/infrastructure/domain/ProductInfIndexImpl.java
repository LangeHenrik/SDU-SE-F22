package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.productmodule.infrastructure.data.TokenParameter;
import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductInfIndexImpl implements ProductInfIndex{

    @Override
    public void indexProducts(List<Product> products) {
        for (Product product : products) {
            List<String> tokenizedProduct = tokenize(product);
            List<String> filteredTokens = tokenFilter(tokenizedProduct);

            //PIM3.indexProducts(filteredTokens, product)
        }
    }

    private List<String> tokenFilter(List<String> tokens){
        //tokens = BIM4.filter(tokens);
        //tokens = CMS.filter(tokens);
        //tokens = PIM4.filter(tokens);
        return tokens;
    }

    private List<String> tokenize(Product product){
        TokenParameter tokenParameter = ProductIndexInfrastructure.getInstance().getTokenParameter();
        List<String> tokens = this.extractData(product, tokenParameter.getDelimiter());
        for (String token : tokens){
             for (String ignoredChar : tokenParameter.getIgnoredChars()){
                 if(token.contains(ignoredChar)){
                     token = token.replaceAll(ignoredChar, "");
                 }
             }
        }
        return tokens;
    }

    private List<String> extractData(Product product, String delimiter) {
        ArrayList<String> productData = new ArrayList<>();
        for (ProductAttribute attr : ProductAttribute.values()) {
            productData.addAll(List.of(product.get(attr).split(delimiter)));
        }
        productData.addAll(product.getLocations());
        return productData;
    }
}
