package dk.sdu.se_f22.BrandModule.Management.services;

import dk.sdu.se_f22.SharedLibrary.models.Brand;

import java.util.List;

public interface IJsonService {
    public List<Brand> deserializeBrand();
}
