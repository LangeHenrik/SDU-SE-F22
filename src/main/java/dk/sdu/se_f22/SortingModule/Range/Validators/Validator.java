package dk.sdu.se_f22.SortingModule.Range.Validators;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;

public class Validator {
    public static void NoNegativeValue(double v) throws InvalidFilterException {
        if (v < 0){
            throw new InvalidFilterException("Input is negative.");
        }
    }


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
