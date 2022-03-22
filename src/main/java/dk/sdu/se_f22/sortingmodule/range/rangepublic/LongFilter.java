package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LongFilter extends RangeFilterClass{
    private final long DB_MIN;
    private final long DB_MAX;
    private long userMin;
    private long userMax;

    public LongFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, long dbMin, long dbMax) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE);
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }

    @Override
    public FilterTypes getType() {
        return FilterTypes.LONG;
    }


    @Override
    public boolean equals(Object other) {
        if(! super.equals(other)) {
            return false;
        }

        if(! (other instanceof LongFilter otherFilter)){
            return false;
        }

        if(otherFilter.getDbMinLong() != this.getDbMinLong()){
            return false;
        }

        if(otherFilter.getDbMaxLong() != this.getDbMaxLong()){
            return false;
        }

        if(otherFilter.getUserMinLong() != this.getUserMinLong()){
            return false;
        }

        if(otherFilter.getUserMaxLong() != this.getUserMaxLong()){
            return false;
        }

        return true;
    }

    @Override
    public Collection<RangeSearchResultMock> useFilter(Collection<RangeSearchResultMock> inputs) {
        // Filter inputs based on min and max value.
        // Only filter and remove the input if it is below min or above max
        List<RangeSearchResultMock> filteredResults = new ArrayList<>();
        for (RangeSearchResultMock searchResultMock : inputs) {
            //assumes that searchResultMock.getAttributes returns a hashmap of attributes where the keys are the names of the attributes
            //assumes that hashmaps return null when key is not found
            //if it returns an Exception instead, then simply add the product to filteredResults
            try {
//                long attributeValue = searchResultMock.getAttributes().get(this.getProductAttribute());
                // Uncommented because searchresult needs more work to figure how to get the actual long value
                long attributeValue = 0;
                if ((attributeValue < this.userMin || attributeValue > this.userMax)) {
                    continue;
                }//guard clause
            } catch (NullPointerException e) {
                //if the product attribute does not exist, simply add the result to the list below.
                // if this behaviour is desired to change uncomment the line below:
                //continue;
            }

            filteredResults.add(searchResultMock);
        }

        return filteredResults;
    }

    @Override
    public long getDbMinLong() {
        return DB_MIN;
    }

    @Override
    public long getDbMaxLong() {
        return DB_MAX;
    }

    @Override
    public long getUserMinLong() {
        return userMin;
    }

    @Override
    public long getUserMaxLong() {
        return userMax;
    }

    @Override
    public long setUserMin(long userMin) {
        this.userMin = userMin;
        return this.userMin;
    }

    @Override
    public long setUserMax(long userMax) {
        this.userMax = userMax;
        return this.userMax;
    }
}
