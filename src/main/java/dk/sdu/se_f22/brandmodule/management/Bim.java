package dk.sdu.se_f22.brandmodule.management;

import dk.sdu.se_f22.brandmodule.management.persistence.IPersistence;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import dk.sdu.se_f22.brandmodule.management.services.IIndexingService;
import dk.sdu.se_f22.brandmodule.management.services.IndexingService;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.ArrayList;
import java.util.List;

public class Bim implements IBim {
    IPersistence persistence;
    IIndexingService indexingService;

    public Bim() {
        persistence = new Persistence();
        indexingService = new IndexingService();
    }

    @Override
    public void createBrand(List<Brand> brands) {
        persistence.addOrUpdateBrands(brands);
    }

    @Override
    public void createBrand(Brand brand) {
        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        persistence.addOrUpdateBrands(brands);
    }

    @Override
    public Brand getBrand(int id) {
        return persistence.getBrand(id);
    }

    @Override
    public Brand getBrand(String name) {
        return persistence.getBrand(name);
    }

    @Override
    public List<Brand> getAllBrands() {
        return persistence.getAllBrands();
    }

    @Override
    public void removeBrand(Brand brand) {
        persistence.deleteBrand(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        createBrand(brand);
        return brand;
    }

    @Override
    public void setIndexInterval(int indexInterval) {
        persistence.setIndexingInterval(indexInterval);
    }

    @Override
    public int getIndexingInterval() {
        return persistence.getIndexingInterval();
    }

    @Override
    public void seedDatabase() {
        persistence.seedDatabase();
    }

    @Override
    public void startIndexInterval() {
        indexingService.StartIndexInterval();
    }

    @Override
    public void getBrandIndex() {
        indexingService.getBrandIndex();
    }
}