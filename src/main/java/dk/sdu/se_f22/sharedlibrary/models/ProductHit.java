package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

/** The class proposed by group 4.4 to use as the return type of search hits.
 * <br>
 * It includes a constructor {@link ProductHit#ProductHit(BaseProduct)}, which simply takes a {@link BaseProduct} as its input,
 * and then parses it into the correct attribute values.
 */
public class ProductHit {
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

    /** The constructor for setting all attributes at once including those that are optional
     */
    public ProductHit(UUID uuid, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description, String size, double clockspeed, double weight) {
        this(uuid, averageUserReview, inStock, ean, price, publishedDate, expirationDate, category, name, description);
        this.size = size;
        this.clockspeed = clockspeed;
        this.weight = weight;
    }

    /**This constructor sets all the required value, but leaves the optional values as null.
     * <br>
     * Except for clockspeed and weight, which are doubles and thus not capable of being null.
     */
    public ProductHit(UUID uuid, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description) {
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
    }

    /** This parses the hard to use Product into a much more user friendly ProductHit.<br>
     * It is proposed by group 4.4 that this representation of a Product is used as the return type in searchHits.
     *
     * @param baseProduct a product
     * @throws DateTimeParseException if The dates are in a non parseable format
     * @throws NumberFormatException if any of the attributes, that are numeric, is unparseable
     */
    public ProductHit(BaseProduct baseProduct) throws DateTimeParseException, NumberFormatException  {
        String stringId = baseProduct.get(ProductAttribute.ID);
        this.uuid = UUID.fromString(stringId);

        this.averageUserReview = Double.parseDouble(baseProduct.get(ProductAttribute.AVERAGE_USER_REVIEW));
        this.inStock = baseProduct.getLocations();
        this.ean = Long.parseLong(baseProduct.get(ProductAttribute.EAN));
        this.price = Double.parseDouble(baseProduct.get(ProductAttribute.PRICE));
        this.publishedDate = Instant.parse(baseProduct.get(ProductAttribute.PUBLISHED_DATE) + 'Z');
        this.expirationDate = Instant.parse(baseProduct.get(ProductAttribute.EXPIRATION_DATE) + 'Z');
        this.category = baseProduct.get(ProductAttribute.CATEGORY);
        this.name = baseProduct.get(ProductAttribute.NAME);
        this.description = baseProduct.get(ProductAttribute.DESCRIPTION);

        if (!baseProduct.get(ProductAttribute.SIZE).equals("unavailable")){
            this.size = baseProduct.get(ProductAttribute.SIZE);
        }

        if (!baseProduct.get(ProductAttribute.CLOCKSPEED).equals("unavailable")){
            this.clockspeed = Double.parseDouble(baseProduct.get(ProductAttribute.CLOCKSPEED));
        }

        if (!baseProduct.get(ProductAttribute.WEIGHT).equals("unavailable")){
            this.weight = Double.parseDouble(baseProduct.get(ProductAttribute.WEIGHT));
        }
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

    public long getLongValue(String productAttribute) {
        if(productAttribute.equals("ean")){
            return this.getEan();
        }
        throw new IllegalArgumentException(productAttribute + "does not exist as a double attribute: ");
    }
}
