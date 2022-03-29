package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.util.List;

public class BrandIndex implements IndexInterface {

    private Connection DBConn = DBConnection.getConnection();

    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        return null;
    }

    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {

    }
}

