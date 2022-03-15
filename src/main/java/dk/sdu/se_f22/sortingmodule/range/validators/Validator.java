package dk.sdu.se_f22.sortingmodule.range.validators;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;

public class Validator {
    public static void NoNegativeValue(double v) throws InvalidFilterException {
        if (v < 0){
            throw new InvalidFilterException("Input is negative.");
        }
    }


    //TODO Make this validator also throw an exception if the strings contain special characters at all
    public static void NoSpecialCharacters(String s) throws InvalidFilterException{
        String[] characters = {"%", "/", "!", "#", "", "(", ")", "=", "{", "}", " ", "", ".", "-", "*"};

        for (String c : characters){
            if (s.equals(c)) {
                throw new InvalidFilterException("Contained only a special character.");
            }
        }
    }

    public static void MaxLessThanMin(double Min, double Max) throws InvalidFilterException{
        if (Min > Max) {
            throw new InvalidFilterException("Min cannot be greater than Max");
        }
    }
}
