package dk.sdu.se_f22;

import dk.sdu.se_f22.searchmodule.twowaysynonyms.TwoWaySynonym;

public class Main {
    public static void main(String[] args) {
        TwoWaySynonym operator = TwoWaySynonym.getInstance();
        operator.create("Computer");
        operator.create("Software");
        System.out.println(operator.read("Computer").synonym());
        //System.out.println(operator.readAll(""));
    }
}
