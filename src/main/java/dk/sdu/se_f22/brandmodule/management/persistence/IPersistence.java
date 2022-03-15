package dk.sdu.se_f22.brandmodule.management.persistence;

import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.List;

public interface IPersistence {
    Brand getBrand(int id);
    Brand getBrand(Brand brand);
    void deleteBrand(int id);
    void deleteBrand(Brand brand);
    void addOrUpdateBrands(List<Brand> brands);
    void setupDatabase();
    void seedDatabase();
}