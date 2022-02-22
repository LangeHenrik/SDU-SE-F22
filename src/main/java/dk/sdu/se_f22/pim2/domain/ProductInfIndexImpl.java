package dk.sdu.se_f22.pim2.domain;

import dk.sdu.se_f22.pim2.Product;
import dk.sdu.se_f22.pim2.ProductIndexInfrastructure;
import dk.sdu.se_f22.pim2.data.TokenParameter;

import java.util.List;

public class ProductInfIndexImpl implements  ProductInfIndex{
    @Override
    public void indexProducts(List<Product> products) {
        // Blank
    }

    private List<String> tokenFilter (List<String> tokens){
        //tokens = BIM4.filter(tokens);
        //tokens = CMS.filter(tokens);
        //tokens = PIM4.filter(tokens);
        return tokens;
    }

    private List<String> tokenize (Product product){
        List<String> tokens; //Data extraction
        TokenParameter ignoredChars1 = ProductIndexInfrastructure.getInstance().getTokenParameter();
        for (String token : tokens){
             for (String ignoredChar : ignoredChars1.getIgnoredChars()){
                 if(token.contains(ignoredChar)){
                     token = token.replaceAll(ignoredChar, "");
                 }

             }
        }
        return tokens;
    }
}
