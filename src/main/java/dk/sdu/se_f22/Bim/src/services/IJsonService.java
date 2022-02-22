package dk.sdu.se_f22.Bim.src.services;

import models.Brand;

import java.util.List;

public interface IJsonService {
    public List<Brand> deserializeBrand();
}
