package dk.sdu.se_f22.brandmodule.Infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import java.util.List;

public interface InfrastructureInterface {
    List<Brand> getBrandsFromSearchTokens(List<String> tokens);

    void indexBrands(List<Brand> brands);

    void setTokenizationParameters(String delimiterRegex, String ignoreRegex);
}
