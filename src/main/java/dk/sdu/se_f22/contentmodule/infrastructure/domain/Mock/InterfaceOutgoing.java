package dk.sdu.se_f22.contentmodule.infrastructure.domain.Mock;


import java.util.ArrayList;
import java.util.List;

public class InterfaceOutgoing {

    public void index(List<String> tokens, int id){

    }

    public void updateSingular(List<String> tokens, int id){

    }

    public void delete(int id){

    }

    public ArrayList<Integer> search(List<String> searchTokens){
        ArrayList<Integer> dummyList = new ArrayList<>();
        dummyList.add(3);
        dummyList.add(67);

        return dummyList;
    }
}
