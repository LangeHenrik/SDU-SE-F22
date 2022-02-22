package dk.sdu.se_f22.pim2.domain;

import dk.sdu.se_f22.pim2.Product;
import dk.sdu.se_f22.pim2.ProductIndexInfrastructure;
import dk.sdu.se_f22.pim2.data.TokenParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductInfIndexImpl implements  ProductInfIndex{
    @Override
    public void indexProducts(List<Product> products) {
        // Blank
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
        return new ArrayList<>(
            Arrays.asList(product.description.split(delimiter))
        );
    }
}
