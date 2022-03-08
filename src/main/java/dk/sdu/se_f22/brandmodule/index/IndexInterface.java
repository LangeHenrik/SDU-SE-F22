package dk.sdu.se_f22.brandmodule.index;

import java.util.ArrayList;
import java.util.List;

public interface IndexInterface {
    public Object searchBrandIndex(List<String> tokens);

    public void indexBrandInformation(String brandName, List<String> tokens);
}

