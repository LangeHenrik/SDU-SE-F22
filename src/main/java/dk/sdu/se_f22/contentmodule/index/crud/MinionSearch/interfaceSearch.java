package dk.sdu.se_f22.contentmodule.index.crud.MinionSearch;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;

public class interfaceSearch {

    public static ArrayList<Integer> Search(ArrayList<String> input){
        Database db = new Database();
        System.out.println(input);
        TokenClass temp2 = new TokenClass(db);
        ArrayList<String> test = temp2.tokenForhold(input);

        ArrayList<Integer> returnListSorted = new ArrayList<>();
        for(String i : test){
            returnListSorted.add(Integer.parseInt(i));
        }
        return returnListSorted;
    }
}
