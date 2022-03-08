package dk.sdu.se_f22.sortingmodule.range.validators;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;

public interface ValidatorInterface {
    void validate(String value) throws InvalidFilterException;

    void validate(double input) throws InvalidFilterException;
}
