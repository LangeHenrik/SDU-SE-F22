package dk.sdu.se_f22.brandmodule.management.persistence;

import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.List;

public interface IPersistence {
    Brand getBrand(int id);
    Brand getBrand(String name);
    List<Brand> getAllBrands();
    boolean deleteBrand(int id);
    boolean deleteBrand(Brand brand);
    boolean databaseIndexer();
    boolean addOrUpdateBrands(List<Brand> brands);
    void seedDatabase();
}