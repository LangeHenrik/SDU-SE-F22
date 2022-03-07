package dk.sdu.se_f22.SortingModule.Range.Validators;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;

public interface ValidatorInterface {
    void validate(String value) throws InvalidFilterException;

    void validate(double input) throws InvalidFilterException;
}
