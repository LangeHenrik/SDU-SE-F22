package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;

import java.time.Instant;

public interface RangeFilter {

    int getId();
    String getName();
    String getDescription();
    String getProductAttribute();

    //todo test this
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

    double setUserMin(double userMin) throws InvalidFilterTypeException;
    Instant setUserMin(Instant userMin) throws InvalidFilterTypeException;
    long setUserMin(long userMin) throws InvalidFilterTypeException;

    double setUserMax(double userMax) throws InvalidFilterTypeException;
    Instant setUserMax(Instant userMax) throws InvalidFilterTypeException;
    long setUserMax(long userMax) throws InvalidFilterTypeException;
}
