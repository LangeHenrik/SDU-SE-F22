package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TimeFilter extends RangeFilterClass{
    private final Instant DB_MIN;
    private final Instant DB_MAX;
    private Instant userMin;
    private Instant userMax;

    public TimeFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, Instant dbMin, Instant dbMax) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE);
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }
    public TimeFilter(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, Instant dbMin, Instant dbMax) {
        super( NAME, DESCRIPTION, PRODUCT_ATTRIBUTE);
        DB_MIN = dbMin;
        DB_MAX = dbMax;

    }
    @Override
    public FilterTypes getType() {
        return FilterTypes.INSTANT;
    }


    @Override
    public boolean equals(Object other) {
        if(! super.equals(other)) {
            return false;
        }

        if(! (other instanceof TimeFilter otherFilter)){
            return false;
        }

        if(otherFilter.getDbMinInstant() != this.getDbMinInstant()){
            return false;
        }

        if(otherFilter.getDbMaxInstant() != this.getDbMaxInstant()){
            return false;
        }

        if(otherFilter.getUserMinInstant() != this.getUserMinInstant()){
            return false;
        }

        if(otherFilter.getUserMaxInstant() != this.getUserMaxInstant()){
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
//                Instant attributeValue = searchResultMock.getAttributes().get(this.getProductAttribute());
                // Uncommented because searchresult needs more work to figure how to get the actual Instant value
                Instant attributeValue = Instant.now();
                if ((attributeValue.isBefore(this.userMin) || attributeValue.isAfter(this.userMax))) {
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
