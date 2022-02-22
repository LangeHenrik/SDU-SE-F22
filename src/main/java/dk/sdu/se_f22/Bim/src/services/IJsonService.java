package dk.sdu.se_f22.Bim.src.services;

import dk.sdu.se_f22.Bim.src.models.Brand;
import java.util.List;

public interface IJsonService {
    public List<Brand> deserializeBrand();
}
