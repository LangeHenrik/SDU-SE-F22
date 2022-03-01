package dk.sdu.se_f22.ProductModule.Management;

public enum ProductAttribute {

    UUID("id"),
    ID("pId"),
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

    String alias;


    ProductAttribute(String alias){
        this.alias = alias;
    }
}
