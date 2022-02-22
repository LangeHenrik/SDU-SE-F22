package dk.sdu.se_f22.pim2.domain;

import dk.sdu.se_f22.pim2.Product;

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

}
