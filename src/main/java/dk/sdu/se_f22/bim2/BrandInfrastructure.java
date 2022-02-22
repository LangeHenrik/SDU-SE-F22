package dk.sdu.se_f22.bim2;

import java.util.List;
import dk.sdu.se_f22.dataclasses.Brand;

public interface BrandInfrastructure {
    List<Brand> getBrandsFromSearchTokens(List<String> tokens);

    void indexBrands(List<Brand> brands);

    void setTokenizationParameters(String delimiter, String ignore);
}
