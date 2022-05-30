package dk.sdu.se_f22.brandmodule.index;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import java.util.List;

public interface IndexInterface {
    List<Brand> searchBrandIndex(List<String> tokens);

    void indexBrandInformation(Brand brand, List<String> tokens);
}

