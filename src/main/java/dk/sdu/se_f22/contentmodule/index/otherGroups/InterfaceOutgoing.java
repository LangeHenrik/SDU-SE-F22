package dk.sdu.se_f22.contentmodule.index.otherGroups;

import crud.MinionSearch.interfaceSearch;
import dk.sdu.se_f22.contentmodule.index.crud.addPage;
import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;

public class InterfaceOutgoing {


    public void index(Database db, String[] array, int fID) {
        addPage.add(db, array, fID);
    }

    public ArrayList<Integer> search(ArrayList<String> input){
        return interfaceSearch.Search(input);
    }
}
