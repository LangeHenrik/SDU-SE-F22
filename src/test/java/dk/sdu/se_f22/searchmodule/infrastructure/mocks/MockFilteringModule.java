package dk.sdu.se_f22.searchmodule.infrastructure.mocks;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;
import java.util.ArrayList;

public class MockFilteringModule implements Filterable {
    private static MockFilteringModule instance;

    private MockFilteringModule(){}

    public static MockFilteringModule getInstance(){
        if(instance == null){
            instance = new MockFilteringModule();
        }
        return instance;
    }

    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        tokens.remove(tokens.size() - 1);
        return tokens;
    }
}
