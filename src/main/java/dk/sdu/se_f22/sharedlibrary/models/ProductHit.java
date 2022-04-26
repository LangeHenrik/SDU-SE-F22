package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

/** The class proposed by group 4.4 to use as the return type of search hits.
 * <br>
 * It includes a constructor {@link ProductHit#ProductHit(Product)}, which simply takes a {@link Product} as its input,
 * and then parses it into the correct attribute values.
 */
public class ProductHit implements Comparable<ProductHit>{
    UUID uuid;
    double averageUserReview;
    double price;
    double clockspeed;
    double weight;
    long ean;
    String size;
    String category;
    String name;
    String description;
    Instant publishedDate;
    Instant expirationDate;
    List<String> inStock;
    int hitNum;

    /** The constructor for setting all attributes at once including those that are optional
     */
    public ProductHit(UUID uuid, double averageUserReview, List<String> inStock, long ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description, String size, double clockspeed, double weight) {
        this(uuid, averageUserReview, inStock, ean, price, publishedDate, expirationDate, category, name, description);
        this.size = size;
        this.clockspeed = clockspeed;
        this.weight = weight;
    }

    /**This constructor sets all the required value, but leaves the optional values as null.
     * <br>
     * Except for clockspeed and weight, which are doubles and thus not capable of being null.
     */
    public ProductHit(UUID uuid, double averageUserReview, List<String> inStock, long ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description) {
        this.uuid = uuid;
        this.averageUserReview = averageUserReview;
        this.inStock = inStock;
        this.ean = ean;
        this.price = price;
        this.publishedDate = publishedDate;
        this.expirationDate = expirationDate;
        this.category = category;
        this.name = name;
        this.description = description;
        hitNum = 0;
    }

    /** This parses the hard to use Product into a much more user friendly ProductHit.<br>
     * It is proposed by group 4.4 that this representation of a Product is used as the return type in searchHits.
     *
     * @param product a product
     * @throws DateTimeParseException if The dates are in a non parseable format
     * @throws NumberFormatException if any of the attributes, that are numeric, is unparseable
     */
    public ProductHit(Product product) throws DateTimeParseException, NumberFormatException  {
        String stringId = product.get(ProductAttribute.ID);
        this.uuid = UUID.fromString(stringId);

        this.averageUserReview = Double.parseDouble(product.get(ProductAttribute.AVERAGE_USER_REVIEW));
        this.inStock = product.getLocations();
        this.ean = Long.parseLong(product.get(ProductAttribute.EAN));
        this.price = Double.parseDouble(product.get(ProductAttribute.PRICE));
        this.publishedDate = Instant.parse(product.get(ProductAttribute.PUBLISHED_DATE) + 'Z');
        this.expirationDate = Instant.parse(product.get(ProductAttribute.EXPIRATION_DATE) + 'Z');
        this.category = product.get(ProductAttribute.CATEGORY);
        this.name = product.get(ProductAttribute.NAME);
        this.description = product.get(ProductAttribute.DESCRIPTION);

        if (!product.get(ProductAttribute.SIZE).equals("unavailable")){
            this.size = product.get(ProductAttribute.SIZE);
        }

        if (!product.get(ProductAttribute.CLOCKSPEED).equals("unavailable")){
            this.clockspeed = Double.parseDouble(product.get(ProductAttribute.CLOCKSPEED));
        }

        if (!product.get(ProductAttribute.WEIGHT).equals("unavailable")){
            this.weight = Double.parseDouble(product.get(ProductAttribute.WEIGHT));
        }
    }


    /** This constructor is for test use only by gorup 2.3
     * Constructor is limited to only setting the values used in the indexing process, and therefore only populates
     * the needed attributes with values. Should not be used other than for testing purposes.
     */
    public ProductHit(String name, String description, String category){
        this.name = name;
        this.description = description;
        this.category = category;
    }



    /**@param attributeName The name of the attribute to retrieve
     * @return The value of the attribute referenced
     * @throws IllegalArgumentException if the attribute input does not correspond to any of the attributes, whose value is a double
     */
    public double getDoubleValue(String attributeName){
        return switch (attributeName) {
            case ("price") -> this.getPrice();
            case ("averageUserReview") -> this.getAverageUserReview();
            case ("clockspeed") -> this.getClockspeed();
            case ("weight") -> this.getWeight();
            default -> throw new IllegalArgumentException(attributeName + "does not exist as a double attribute: ");
        };
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getAverageUserReview() {
        return averageUserReview;
    }

    public List<String> getInStock() {
        return inStock;
    }

    public long getEan() {
        return ean;
    }

    public double getPrice() {
        return price;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public double getClockspeed() {
        return clockspeed;
    }

    public double getWeight() {
        return weight;
    }

    public int getHitNum(){
        return hitNum;
    }
    public void setHitNum(int num){
        hitNum = num;
    }

    public long getLongValue(String productAttribute) {
        if(productAttribute.equals("ean")){
            return this.getEan();
        }
        throw new IllegalArgumentException(productAttribute + "does not exist as a double attribute: ");
    }
    @Override
    public int compareTo(ProductHit o) {
        if (this.getHitNum() < o.getHitNum()){
            return 1;
        }
        else if (this.getHitNum() > o.getHitNum()){
            return -1;
        }
        else{
            return 0;
        }
    }
    @Override
    public String toString(){
        return "Name : "+ getName()+ " Hits : "+getHitNum() +"\n";
    }
}
