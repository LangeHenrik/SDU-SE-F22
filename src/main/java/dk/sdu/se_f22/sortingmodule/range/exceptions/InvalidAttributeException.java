package dk.sdu.se_f22.sortingmodule.range.exceptions;

import java.util.List;

/**
 * Is thrown when a filter is used to filter a list,
 * but the productAttribute of this filter is not valid.
 */
public class InvalidAttributeException extends RangeFilterException{
    /**
     * Will print:<br>
     * "The attribute: " + invalidAttribute + " was invalid. The valid attributes are: " + validAttributes.toString()
     * @param invalidAttribute The attribute saved on the filter
     * @param validAttributes The list of valid attributes for this filter
     */
    public InvalidAttributeException(String invalidAttribute, List<String> validAttributes){
        super("The attribute: " + invalidAttribute + " was invalid. The valid attributes are: " + validAttributes.toString());
    }
}
