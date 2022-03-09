package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.range.internalfilter.KeepOrNotEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class InternalFilter {
    private final double min;
    private final double max;
    private final String productAttribute;

    public InternalFilter(double min, double max, String productAttribute) {
        this.min = min;
        this.max = max;
        this.productAttribute = productAttribute;
    }

    /** Filters an input Collection and only removes entries from the list if they:<br>
     * a. <br>
     * Have the attribute that this filter is filtering on <br>
     * b. <br>
     * The attribute value is either less than the allowed (by this filter) minimum value,
     * or it is larger than the allowed maximum value.
     *
     * @param inputs The collection that contains ProductHit objects
     * @return The collection of ProductHits, which have now been filtered.
     */
    public Collection<RangeSearchResultMock> useFilter(Collection<RangeSearchResultMock> inputs) {
        // Filter inputs based on min and max value.
        // Only filter and remove the input if it is below min or above max
        List<RangeSearchResultMock> filteredResults = new ArrayList<>();
        for (RangeSearchResultMock searchResultMock : inputs) {
            //assumes that searchResultMock.getAttributes returns a hashmap of attributes where the keys are the names of the attributes
            //assumes that hashmaps return null when key is not found
            //if it returns an Exception instead, then simply add the product to filteredResults
            try {
                double attributeValue = searchResultMock.getAttributes().get(this.productAttribute);
                if ((attributeValue < this.min || attributeValue > this.max)) {
                    continue;
                }//guard clause
            } catch (NullPointerException e) {
                //if the product attribute does not exist, simply add the result to the list below.
                //i.e. keep it in the list of valid hits.
                // if this behaviour is no longer wanted, uncomment the line below:
                //continue;
            }

            filteredResults.add(searchResultMock);
        }

        return filteredResults;
    }

//    /** Filters an input Collection and only removes entries from the list if they:<br>
//     * a. <br>
//     * Have the attribute that this filter is filtering on <br>
//     * b. <br>
//     * The attribute value is either less than the allowed (by this filter) minimum value,
//     * or it is larger than the allowed maximum value.
//     *
//     * @param inputs The collection that contains ProductHit objects
//     * @return The collection of ProductHits, which have now been filtered.
//     */
//    public Collection<ProductHit> useFilter(Collection<ProductHit> inputs) {
//        List<ProductHit> filteredResults = new ArrayList<>();
//        for (ProductHit productHit : inputs) {
//            //check as double
//            KeepOrNotEnum result = keepOrNotAsDouble(productHit);
//            if(result == KeepOrNotEnum.KEEP){
//                filteredResults.add(productHit);
//            }
//
//            //Skips to the next product if the result was either keep or remove, because it means the attribute was found
//            // This means that if remove was returned the product has not been added to the filtered list.
//            if(result != KeepOrNotEnum.UNKNOWN){
//                continue;
//            }
//
//            result = keepOrNotAsLong(productHit);
//            if(result == KeepOrNotEnum.KEEP){
//                filteredResults.add(productHit);
//            }
//
//            if(result != KeepOrNotEnum.UNKNOWN){
//                continue;
//            }
//
//            result = keepOrNotAsInstant(productHit);
//            if(result == KeepOrNotEnum.KEEP){
//                filteredResults.add(productHit);
//            }
//
//            if(result != KeepOrNotEnum.UNKNOWN){
//                continue;
//            }
//
//
//            filteredResults.add(productHit);
//        }
//
//        return filteredResults;
//    }

    private KeepOrNotEnum keepOrNotAsInstant(ProductHit productHit) {
        return KeepOrNotEnum.UNKNOWN;
    }

    private KeepOrNotEnum keepOrNotAsLong(ProductHit productHit) {
        try {
            long attributeValue = productHit.getLongValue(this.productAttribute);
            if ((attributeValue < this.min || attributeValue > this.max)) {
                //don't add because the hit was outside the range
                return KeepOrNotEnum.REMOVE;
            }
            //add and continue because we found the correct type of the attribute
            return KeepOrNotEnum.KEEP;
        } catch (IllegalArgumentException ignored) {
            // This parameter was not of the type double
            //moving on in the type check
        }
        return KeepOrNotEnum.UNKNOWN;
    }

    private KeepOrNotEnum keepOrNotAsDouble(ProductHit productHit) {
        try {
            double attributeValue = productHit.getDoubleValue(this.productAttribute);
            if ((attributeValue < this.min || attributeValue > this.max)) {
                //don't add because the hit was outside the range
                return KeepOrNotEnum.REMOVE;
            }
            //add and continue because we found the correct type of the attribute
            return KeepOrNotEnum.KEEP;
        } catch (IllegalArgumentException ignored) {
            // This parameter was not of the type double
            //moving on in the type check
        }
        return KeepOrNotEnum.UNKNOWN;
    }
}
