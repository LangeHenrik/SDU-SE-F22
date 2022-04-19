package dk.sdu.se_f22.sortingmodule.range.exceptions;

/**
 * Is thrown when the type of the filter (as read from the database) does
 * not match any of the types, we have implemented in java.
 * Should never happen, but we still have a safeguard for it.
 */
public class UnknownFilterTypeException extends Exception {
    public UnknownFilterTypeException(String s) {
        super(s);
    }
}
