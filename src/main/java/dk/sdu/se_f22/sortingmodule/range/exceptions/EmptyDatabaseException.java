package dk.sdu.se_f22.sortingmodule.range.exceptions;

/**
 * EmptyDatabaseException is thrown when readAll is called while the database is empty.
 */
public class EmptyDatabaseException extends Exception {
    public EmptyDatabaseException(String message) { super(message); }
}
