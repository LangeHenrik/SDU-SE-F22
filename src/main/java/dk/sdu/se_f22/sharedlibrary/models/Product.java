package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.BaseProduct;
import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

/** The class is used as the common representation of a product. <br>
 * Is therefore the type used for products in {@link dk.sdu.se_f22.sharedlibrary.SearchHits}
 * <br>
 * It includes among others a constructor {@link Product#Product(BaseProduct)}, which simply takes a {@link BaseProduct} as its input,
 * and then parses the attribute values from the {@link BaseProduct} supplied.<br>
 * This class has a getter for each specific product attribute (i.e. {@link Product#getPrice()}).<br><br>
 * <i>Formerly known as <b>ProductHit</b></i>
 */
public class Product {
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
    public Product(UUID uuid, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description, String size, double clockspeed, double weight) {
        this(uuid, averageUserReview, inStock, ean, price, publishedDate, expirationDate, category, name, description);
        this.size = size;
        this.clockspeed = clockspeed;
        this.weight = weight;
    }

    /**This constructor sets all the required value, but leaves the optional values as null.
     * <br>
     * Except for clockspeed and weight, which are doubles and thus not capable of being null.
     */
    public Product(UUID uuid, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description) {
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

    /** This parses a {@link BaseProduct} into a Product.<br>
     *
     * @param baseProduct a product
     * @throws DateTimeParseException if The dates supplied are in a non parseable format
     * @throws NumberFormatException if any of the attributes, that are numeric, are unparseable
     */
    public Product(BaseProduct baseProduct) throws DateTimeParseException, NumberFormatException  {
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
/*
    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", averageUserReview=" + averageUserReview +
                ", price=" + price +
                ", clockspeed=" + clockspeed +
                ", weight=" + weight +
                ", ean=" + ean +
                ", size='" + size + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", publishedDate=" + publishedDate +
                ", expirationDate=" + expirationDate +
                ", inStock=" + inStock +
                '}';
    }

 */

    @Override
    public String toString() {
        return "Product{" +
                "averageUserReview=" + averageUserReview +
                ", price=" + price +
                ", publishedDate=" + publishedDate +
                ", inStock=" + inStock +
                '}'+"\n";
    }
}
