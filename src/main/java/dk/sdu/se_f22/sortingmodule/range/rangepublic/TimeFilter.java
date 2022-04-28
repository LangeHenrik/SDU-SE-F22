package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.ProductHit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class TimeFilter extends RangeFilterClass {
    private final Instant DB_MIN;
    private final Instant DB_MAX;
    private Instant userMin;
    private Instant userMax;

    private static final List<String> validAttributes = List.of(new String[]{"publishedDate", "expirationDate"});


    public TimeFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, Instant dbMin, Instant dbMax) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE,
                List.of(new String[]{"ean"}));
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }


    public TimeFilter(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, Instant dbMin, Instant dbMax) {
        super(NAME, DESCRIPTION, PRODUCT_ATTRIBUTE,
                List.of(new String[]{"ean"}));
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }


    @Override
    public FilterTypes getType() {
        return FilterTypes.TIME;
    }


    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            System.out.println("super");
            return false;
        }

        if (!(other instanceof TimeFilter otherFilter)) {
            System.out.println("non time");
            return false;
        }


        if (instantsDifferNullSafe(otherFilter.getDbMinInstant(), this.getDbMinInstant())) {
            System.out.println("dbmin");
            return false;
        }

        if (instantsDifferNullSafe(otherFilter.getDbMaxInstant(), this.getDbMaxInstant())) {
            System.out.println("dbmax");
            return false;
        }

        if (instantsDifferNullSafe(otherFilter.getUserMinInstant(), this.getUserMinInstant())) {
            System.out.println("usermin");
            return false;
        }

        if (instantsDifferNullSafe(otherFilter.getUserMaxInstant(), this.getUserMaxInstant())) {
            System.out.println("usermax");
            return false;
        }

        return true;
    }

    private boolean instantsDifferNullSafe(Instant otherInstant, Instant thisInstant) {
        if (otherInstant == null && thisInstant == null) {
            return false;
        } else if (otherInstant == null || thisInstant == null) {
            return true;
        } else {
            return !otherInstant.equals(thisInstant);
        }
    }

    @Override
    public String toString() {
        return "TimeFilter{" + super.toString() + ", " +
                "DB_MIN=" + DB_MIN +
                ", DB_MAX=" + DB_MAX +
                ", userMin=" + userMin +
                ", userMax=" + userMax +
                '}';
    }


    @Override
    Collection<ProductHit> filterList(Collection<ProductHit> inputs) {
        // Filter inputs based on min and max value.
        // Only filter and remove the input if it is below min or above max
        List<ProductHit> filteredResults = new ArrayList<>();


        // loop over all the products in the list and access the correct attribute:
        for (ProductHit productHit : inputs) {
            // We use an if else, because it will be faster for only 2 elements
            // if more attributes are added, change implementation to a switch like in DoubleFilter
            if(this.getProductAttribute().equals("publishedDate")){
                if (checkValue(productHit.getPublishedDate())){
                    continue;
                }
                // below suppressed because it improves readability
            } else //noinspection StatementWithEmptyBody
                if (this.getProductAttribute().equals("expirationDate")) {
                if (checkValue(productHit.getExpirationDate())){
                    continue;
                }
            } else {
                // This means the product attribute was neither of the ones we claim to be valid
                // This should not happen, although if it does, the product will not be filtered out
                // with the current implementation.

                // To make the product get filtered out uncomment below line
//                continue;

                // Be aware that this means, that in case one of the filters used is broken, the result will be empty
                // This happens because the product attribute is the same for every product (since it comes from this filter)
            }

            // We have survived the check, without continuing the loop, thus we add the product to the filtered list
            filteredResults.add(productHit);
        }

        return filteredResults;
    }


    /**
     * This method is used to check whether the value of a product
     * lies within the range specified by this filter.
     *
     * @param value - The value to check, assumed to be different from null
     * @return - true if the value is outside the range specified by the filter
     */
    private boolean checkValue(Instant value) {
        if (this.userMin != this.userMax) {
            return value.isBefore(this.userMin) || value.isAfter(this.userMax);
        }

        return value.isBefore(this.DB_MIN) || value.isAfter(this.DB_MAX);
    }

    /**
     * This method is used to check whether the value of a product
     * lies within the range specified by this filter.
     * This method is used when the product attribute is optional
     * (i.e. it may not be set on the product, and thus may have been only been initialized to null)
     *
     * @param value - The value to check
     * @return - true if the value is outside the range specified, but different from null
     */
    private boolean checkValueOptional(Instant value) {
        return value != null && checkValue(value);
    }

    @Override
    public Instant getDbMinInstant() {
        return DB_MIN;
    }

    @Override
    public Instant getDbMaxInstant() {
        return DB_MAX;
    }

    @Override
    public Instant getUserMinInstant() {
        return userMin;
    }

    @Override
    public Instant getUserMaxInstant() {
        return userMax;
    }

    @Override
    public Instant setUserMin(Instant userMin) {
        this.userMin = userMin;
        return this.userMin;
    }

    @Override
    public Instant setUserMax(Instant userMax) {
        this.userMax = userMax;
        return this.userMax;
    }
}
