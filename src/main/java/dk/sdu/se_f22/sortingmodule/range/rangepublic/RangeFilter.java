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
     * @param userMin double value for userMin.
     * @return Return the new value for userMin. 
     * @throws IllegalMinMaxException if userMin is either below DBMin or above either DBMax or userMax
     */
    double setUserMin(double userMin) throws IllegalMinMaxException;

    /**
     * Set the userMin for the filter.
     * Only if the userMin value is bigger than the DBMin value and the userMin is less than the DBMax and userMax if it is set.
     * @param userMin Instant value for userMin.
     * @return Return the new value for userMin.
     * @throws IllegalMinMaxException if userMin is either below DBMin or above either DBMax or userMax
     */
    Instant setUserMin(Instant userMin) throws IllegalMinMaxException;

    /**
     * Set the userMin for the filter.
     * Only if the userMin value is bigger than the DBMin value and the userMin is less than the DBMax and userMax if it is set.
     * @param userMin long value for userMin.
     * @return Return the new value for the userMin.
     * @throws IllegalMinMaxException if userMin is either below DBMin or above either DBMax or userMax
     */
    long setUserMin(long userMin) throws IllegalMinMaxException;

    /**
     * Set the userMax for the filter.
     * Only if the userMax is less than the DBMAx and higher than the DBMin.
     * The userMax has to be higher than the userMin if it is set.
     * @param userMax double value for the user max.
     * @return Return the new value for the userMax.
     * @throws IllegalMinMaxException if userMax is either above DBMax or below either userMax or DbMax
     */
    double setUserMax(double userMax) throws IllegalMinMaxException;

    /**
     * Set the userMax for the filter.
     * Only if the userMax is less than the DBMAx and higher than the DBMin.
     * The userMax has to be higher than the userMin if it is set.
     * @param userMax Instant value for the userMax.
     * @return Return the new value for the userMax.
     * @throws IllegalMinMaxException if userMax is either above DBMax or below either userMax or DbMax
     */
    Instant setUserMax(Instant userMax) throws IllegalMinMaxException;

    /**
     * Set the userMax for the filter.
     * Only if the userMax is less than the DBMAx and higher than the DBMin.
     * The userMax has to be higher than the userMin if it is set.
     * @param userMax long value for the userMax
     * @return Return the new value for the userMax.
     * @throws IllegalMinMaxException if userMax is either above DBMax or below either userMax or DbMax
     */
    long setUserMax(long userMax) throws IllegalMinMaxException;
}
