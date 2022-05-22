package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalMinMaxException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;

import java.time.Instant;

public interface RangeFilter {

    int getId();
    String getName();
    String getDescription();
    String getProductAttribute();

    FilterTypes getType();

    double getDbMinDouble() throws InvalidFilterTypeException;
    Instant getDbMinInstant() throws InvalidFilterTypeException;
    long getDbMinLong() throws InvalidFilterTypeException;

    double getDbMaxDouble() throws InvalidFilterTypeException;
    Instant getDbMaxInstant() throws InvalidFilterTypeException;
    long getDbMaxLong() throws InvalidFilterTypeException;

    double getUserMinDouble() throws InvalidFilterTypeException;
    Instant getUserMinInstant() throws InvalidFilterTypeException;
    long getUserMinLong() throws InvalidFilterTypeException;

    double getUserMaxDouble() throws InvalidFilterTypeException;
    Instant getUserMaxInstant() throws InvalidFilterTypeException;
    long getUserMaxLong() throws InvalidFilterTypeException;

    /**
     * Set the userMin for the filter. 
     * Only if the userMin value is bigger than the DBMin value and the userMin is less than the DBMax and userMax if it is set.
     * @param userMin Double value for userMin.
     * @return Return the new value for userMin. 
     * @throws IllegalMinMaxException
     */
    double setUserMin(double userMin) throws IllegalMinMaxException;
    Instant setUserMin(Instant userMin) throws IllegalMinMaxException;
    long setUserMin(long userMin) throws IllegalMinMaxException;

    double setUserMax(double userMax) throws IllegalMinMaxException;
    Instant setUserMax(Instant userMax) throws IllegalMinMaxException;
    long setUserMax(long userMax) throws IllegalMinMaxException;
}
