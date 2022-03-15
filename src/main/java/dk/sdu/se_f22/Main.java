package dk.sdu.se_f22;

import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Persistence p = new Persistence();

        Brand fd = p.getBrand(3);
        fd.setName("Testf");
        List<Brand> l = new ArrayList<Brand>();
        l.add(fd);
        p.addOrUpdateBrands(l);

    }
}
