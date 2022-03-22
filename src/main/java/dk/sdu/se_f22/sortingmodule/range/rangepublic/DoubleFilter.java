package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoubleFilter extends RangeFilterClass{
    private final double DB_MIN;
    private final double DB_MAX;
    private double userMin;
    private double userMax;

    public DoubleFilter(int ID, String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, double dbMin, double dbMax) {
        super(ID, NAME, DESCRIPTION, PRODUCT_ATTRIBUTE);
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }

    public DoubleFilter(String NAME, String DESCRIPTION, String PRODUCT_ATTRIBUTE, double dbMin, double dbMax) {
        super(NAME, DESCRIPTION, PRODUCT_ATTRIBUTE);
        DB_MIN = dbMin;
        DB_MAX = dbMax;
    }

    @Override
    public FilterTypes getType() {
        return FilterTypes.DOUBLE;
    }

    @Override
    public boolean equals(Object other) {
        if(! super.equals(other)) {
            return false;
        }

        if(! (other instanceof DoubleFilter otherFilter)){
            return false;
        }

        if(otherFilter.getDbMinDouble() != this.getDbMinDouble()){
            return false;
        }

        if(otherFilter.getDbMaxDouble() != this.getDbMaxDouble()){
            return false;
        }

        if(otherFilter.getUserMinDouble() != this.getUserMinDouble()){
            return false;
        }

        if(otherFilter.getUserMaxDouble() != this.getUserMaxDouble()){
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
                double attributeValue = searchResultMock.getAttributes().get(this.getProductAttribute());
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
    public double getDbMinDouble(){
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
