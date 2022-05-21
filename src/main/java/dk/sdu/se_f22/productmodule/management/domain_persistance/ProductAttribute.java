package dk.sdu.se_f22.productmodule.management.domain_persistance;

public enum ProductAttribute {
    
    UUID("id"),
    AVERAGE_USER_REVIEW("averageUserReview"),
    IN_STOCK("inStock"),
    EAN("ean"),
    PRICE("price"),
    PUBLISHED_DATE("publishedDate"),
    EXPIRATION_DATE("expirationDate"),
    CATEGORY("category"),
    NAME("name"),
    DESCRIPTION("description"),
    WEIGHT("weight"),
    SIZE("size"),
    CLOCKSPEED("clockSpeed");
    
    public String alias;
    
    ProductAttribute(String alias){
        this.alias = alias;
    }
    
    public static ProductAttribute fromString(String s){
        for(ProductAttribute pAttr : ProductAttribute.values()){
            if(s.equalsIgnoreCase(pAttr.alias)){
                return pAttr;
            }
        }
        
        return null;
    }
}