package dk.sdu.se_f22.sortingmodule.range.validators;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;

public abstract class NoSpecialCharacters implements ValidatorInterface {
     String[] characters = {"%","/", "!", "#", "", "(", ")", "=", "{", "}", " ", "", ".", "-", "*"};

    @Override
    public void validate(String input) throws InvalidFilterException {
        for (String c : characters){
            if (input.equals(c)) {
                throw new InvalidFilterException("Contained only a special character.");
            }
        }
    }
}
