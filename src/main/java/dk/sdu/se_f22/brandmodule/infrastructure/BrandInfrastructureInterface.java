package dk.sdu.se_f22.brandmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import java.util.List;

public interface BrandInfrastructureInterface extends IndexingModule<Brand> {
    void indexBrands(List<Brand> brands);

    void setTokenizationParameters(String delimiterRegex, String ignoreRegex);
}
