package dk.sdu.se_f22.SortingModule.Range;

import java.util.ArrayList;

class InternalFilter {
    private final double min;
    private final double max;
    private final String productAttribute;

    public InternalFilter(double min, double max, String productAttribute) {
        this.min = min;
        this.max = max;
        this.productAttribute = productAttribute;
    }

    public Object[] useFilter(Object[] inputs){
        // do the filtering instead of this
        ArrayList<Object> toReturn = new ArrayList<>();
        for (Object object : inputs) {
            //assumes that object.getAttributes returns a hashmap of attributes where the keys are the names of the attributes
            //assumes that hashmaps return null when key is not found
            //if it returns an Exception instead, then simply add tha product to toReturn
            // double attributeValue = object.getAttributes().get(this.productAttribute)
//            if ( attributeValue == null || (attributeValue > this.min && attributeValue < this.max)) {
//                toReturn.add(object);
//            }
        }

        return toReturn.toArray();
    }
}
