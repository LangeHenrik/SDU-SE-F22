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
public class ProductHit {
    UUID id;
    double averageUserReview;
    List<String> inStock;
    long ean;
    double price;
    Instant publishedDate;
    Instant expirationDate;
    String category;
    String name;
    String description;
    String size;
    double clockspeed;
    double weight;

    /** The constructor for setting all attributes at once including those that are optional
     */
    public ProductHit(UUID id, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description, String size, double clockspeed, double weight) {
        this(id, averageUserReview, inStock, ean, price, publishedDate, expirationDate, category, name, description);
        this.size = size;
        this.clockspeed = clockspeed;
        this.weight = weight;
    }

    /**This constructor sets all the required value, but leaves the optional values as null.
     * <br>
     * Except for clockspeed and weight, which are doubles and thus not capable of being null.
     */
    public ProductHit(UUID id, double averageUserReview, List<String> inStock, int ean, double price, Instant publishedDate, Instant expirationDate, String category, String name, String description) {
        this.id = id;
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
     * @param product a product
     * @throws DateTimeParseException if The dates are in a non parseable format
     * @throws NumberFormatException if any of the attributes, that are numeric, is unparseable
     */
    public ProductHit(Product product) throws DateTimeParseException, NumberFormatException  {
        String stringId = product.get(ProductAttribute.ID);
        this.id = UUID.fromString(stringId);

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
}
