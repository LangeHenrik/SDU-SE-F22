package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class DoubleFilter extends RangeFilterClass {
    private final double DB_MIN;
    private final double DB_MAX;
    private double userMin;
    private double userMax;


    public DoubleFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, double dbMin, double dbMax) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE,
                List.of(new String[]{"price", "averageUserReview", "clockspeed", "weight"}));
        DB_MIN = dbMin;
        DB_MAX = dbMax;

    }

    public DoubleFilter(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, double dbMin, double dbMax) {
        super(NAME, DESCRIPTION, PRODUCT_ATTRIBUTE,
                List.of(new String[]{"price", "averageUserReview", "clockspeed", "weight"}));
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }

    @Override
    public FilterTypes getType() {
        return FilterTypes.DOUBLE;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        if (!(other instanceof DoubleFilter otherFilter)) {
            return false;
        }

        if (otherFilter.getDbMinDouble() != this.getDbMinDouble()) {
            return false;
        }

        if (otherFilter.getDbMaxDouble() != this.getDbMaxDouble()) {
            return false;
        }

        if (otherFilter.getUserMinDouble() != this.getUserMinDouble()) {
            return false;
        }

        //suppressed because it improves readability
        //noinspection RedundantIfStatement
        if (otherFilter.getUserMaxDouble() != this.getUserMaxDouble()) {
            return false;
        }

        return true;

    }

    @Override
    public String toString() {
        return "DoubleFilter{" + super.toString() + ", " +
                "DB_MIN=" + DB_MIN +
                ", DB_MAX=" + DB_MAX +
                ", userMin=" + userMin +
                ", userMax=" + userMax +
                '}';
    }


    @Override
    Collection<Product> filterList(Collection<Product> inputs){
        // Filter inputs based on min and max value.
        // Only filter and remove the input if it is below min or above max

        if(inputs == null){
            return null;
        }

        List<Product> filteredResults = new ArrayList<>();

        int attributeNumber = switch (this.getProductAttribute()) {
            case "price" -> 1;
            case "averageUserReview" -> 2;
            case "clockspeed" -> 3;
            case "weight" -> 4;
            default -> 0;
        };

        // loop over all the products in the list and access the correct attribute:
        for (Product productHit : inputs) {
            // We use the switch on numbers instead of the string, since we run the switch for each element in the list
            // This version has the highest likelihood of getting compiled to a TableSwitch, which is very fast
            switch (attributeNumber) {
                case 1 -> {
                    if (checkValue(productHit.getPrice())) {
                        continue;
                    }//guard clause
                }
                case 2 -> {
                    if (checkValue(productHit.getAverageUserReview())) {
                        continue;
                    }//guard clause
                }
                case 3 -> {
                    if (checkValueOptional(productHit.getClockspeed())) {
                        continue;
                    }//guard clause
                }
                case 4 -> {
                    if (checkValueOptional(productHit.getWeight())) {
                        continue;
                    }//guard clause
                }
                default -> {
                    // attribute did not match
                    // Currently we let the item get added to the list, uncomment line below to change
//                    continue;

                    // Be aware that uncommenting above line will cause completely empty lists in non strict mode
                    // (this method called directly)
                    // In the case of a filter with an attribute, that is not valid

                    // This case was previously impossible, since we checked, if the attribute is valid
                    // But in non strict mode, it will likely happen, thus it is not a good idea to remove items
                }
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
     * @param value - The value to check
     * @return - true if the value is outside the range specified by the filter
     */
    private boolean checkValue(double value) {
        if (this.userMin != this.userMax) {
            return value < this.userMin || value > this.userMax;
        }

        return value < this.DB_MIN || value > this.DB_MAX;
    }

    /**
     * This method is used to check whether the value of a product
     * lies within the range specified by this filter.
     * This method is used when the product attribute is optional
     * (i.e. it may not be set on the product, and thus may have been only been initialized to 0)
     *
     * @param value - The value to check
     * @return - true if the value is outside the range specified, but different from zero
     */
    private boolean checkValueOptional(double value) {
        return value != 0 && checkValue(value);
    }

    @Override
    public double getDbMinDouble() {
        return DB_MIN;
    }

    @Override
    public double getDbMaxDouble() {
        return DB_MAX;
    }

    @Override
    public double getUserMinDouble() {
        return userMin;
    }

    @Override
    public double getUserMaxDouble() {
        return userMax;
    }

    @Override
    public double setUserMin(double userMin) {
        this.userMin = userMin;
        return this.userMin;
    }

    @Override
    public double setUserMax(double userMax) {
        this.userMax = userMax;
        return this.userMax;
    }
}
