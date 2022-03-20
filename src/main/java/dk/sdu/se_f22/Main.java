package dk.sdu.se_f22;

import dk.sdu.se_f22.searchmodule.twowaysynonyms.Synonym;
import dk.sdu.se_f22.searchmodule.twowaysynonyms.TwoWaySynonym;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TwoWaySynonym operator = TwoWaySynonym.getInstance();
        operator.create("Computer");
        operator.create("Software");
        operator.create("PC", "Computer");
        System.out.println(operator.read("Software").toString());
        ArrayList<Synonym> synonyms = operator.readAll("Computer");
        for (Synonym s : synonyms) {
            System.out.println(s.toString());
        }
    }
}
