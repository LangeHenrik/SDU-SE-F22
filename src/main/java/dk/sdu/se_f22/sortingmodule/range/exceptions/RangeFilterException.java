package dk.sdu.se_f22.sortingmodule.range.exceptions;

/**
 * This Exception is the base of Class of the Exceptions thrown in this module.<br>
 * This allows for cleaner method signatures where exceptions of these types are thrown.
 */
public class RangeFilterException extends Exception{
    public RangeFilterException(String message) {
        super(message);
    }
}
