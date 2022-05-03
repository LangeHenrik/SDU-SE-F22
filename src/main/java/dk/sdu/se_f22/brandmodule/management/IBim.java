package dk.sdu.se_f22.brandmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.List;

public interface IBim {

    //create a new brand in the database
    void createBrand(Brand brand);
    void createBrand(List<Brand> brands);

    //get a brand from database
    Brand getBrand(int id);
    Brand getBrand(String name);
    List<Brand> getAllBrands();

    //remove a brand from the database
    void removeBrand(Brand brand);

    //update brand on the database
    Brand updateBrand(Brand brand);

    //set the index interval
    void setIndexInterval(int indexInterval);
    int  getIndexingInterval();

}
