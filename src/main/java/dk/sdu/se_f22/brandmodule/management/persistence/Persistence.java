package dk.sdu.se_f22.brandmodule.management.persistence;
import dk.sdu.se_f22.brandmodule.management.services.IJsonService;
import dk.sdu.se_f22.brandmodule.management.services.JsonService;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Persistence implements IPersistence {
    private Connection c = null;
    private IJsonService jsonService = null;

    public Persistence() {
        //Connect to database
        jsonService = new JsonService();
        c = DBConnection.getConnection();

        // Seed database
    }

    @Override
    public Brand getBrand(int id)  {
        return brandGetter(null, id);
    }

    @Override
    public Brand getBrand(String name) {
        return brandGetter(name, null);
    }
    
    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brandList = new ArrayList<>();

        try {
            List<Integer> brandIdList = new ArrayList();

            PreparedStatement getBrandId = c.prepareStatement("SELECT id FROM brand");
            ResultSet r = getBrandId.executeQuery();

            while(r.next()){
                brandIdList.add(r.getInt(1));
            }

            for (int i : brandIdList ){
                brandList.add(getBrand(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    private Brand brandGetter(String name, Integer id) {
        setAutoCommit(true);

        Brand b = null;
        try {
            var stmt = c.createStatement();

            ResultSet r;
            if(name == null) {
                PreparedStatement getBrand = c.prepareStatement("select brand.id, brand.name, brand.description, brand.founded, brand.headquarters, producttype.name from brandproducttypejunction as bpj " +
                        "inner join brand on bpj.brandid = brand.id " +
                        "inner join producttype on bpj.productid = producttype.id " +
                        "where brandid = ?;");
                getBrand.setInt(1,id);


                r = getBrand.executeQuery();
            } else{
                PreparedStatement getBrand = c.prepareStatement("select brand.id, brand.name, brand.description, brand.founded, brand.headquarters, producttype.name from brandproducttypejunction as bpj " +
                        "inner join brand on bpj.brandid = brand.id " +
                        "inner join producttype on bpj.productid = producttype.id " +
                        "where brand.name = ?");
                getBrand.setString(1,"name");
                r = getBrand.executeQuery();
            }
            Set<String> productSet = new HashSet<>();

            int counter = 0;
            while(r.next() && r != null){
                if (counter == 0){
                    b = new Brand(
                            r.getInt("id"),
                            r.getString("name"),
                            r.getString("description"),
                            r.getString("founded"),
                            r.getString("headquarters")
                    );
                }

                productSet.add(r.getString(6));
                counter++;
            }

            // Parse to arraylist
            ArrayList<String> productList = new ArrayList<String>(productSet);

            if (!productList.isEmpty())
                b.setProducts(productList);

            stmt.close();
        } catch (SQLException e) {
            rollback();
            e.printStackTrace();
        }

        return b;
    }

    @Override
    public void deleteBrand(Brand brand) {deleteBrand(brand.getId());
    }

    @Override
    public void deleteBrand(int id) {
        setAutoCommit(false);
        try {
            // Delete from both Brand and Junction table
            // Products may be used by another brand, so it won't be deleted
            PreparedStatement deleteBrandJunction = c.prepareStatement("DELETE FROM BrandProductTypeJunction where brandid = ?");
            PreparedStatement deleteBrand = c.prepareStatement(("DELETE FROM Brand where id = ?"));

            deleteBrandJunction.setInt(1,id);
            deleteBrandJunction.execute();

            deleteBrand.setInt(1,id);
            deleteBrand.execute();


            c.commit();
        } catch (SQLException e) {
            rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addOrUpdateBrands(List<Brand> brands) {
        setAutoCommit(false);
        try {
            /* ------ Insert all products ------ */
            // Keep no duplicate products
            Set<String> products = new HashSet<>();
            for (var brand : brands) {
                products.addAll(brand.getProducts());
            }

            // Insert products into database
            for (var product : products) {
                PreparedStatement insertAllProducts = c.prepareStatement("INSERT INTO ProductType (name) VALUES (?) ON CONFLICT DO NOTHING;");

                insertAllProducts.setString(1, product);
                insertAllProducts.execute();
            }

            /* ------ Insert all brands ------ */
            for (var brand : brands) {
                PreparedStatement insertAllBrands = c.prepareStatement("INSERT INTO Brand (name, description, founded, headquarters) VALUES (?,?,?,?) ON CONFLICT DO NOTHING;");

                insertAllBrands.setString(1, brand.getName());
                insertAllBrands.setString(2,brand.getDescription().replaceAll("'", "''"));
                insertAllBrands.setString(3,brand.getFounded());
                insertAllBrands.setString(4, brand.getHeadquarters());

                insertAllBrands.execute();
            }

            /* ------ Insert into junction table ------ */
            for (Brand brand : brands){
                int brandID = 0;

                PreparedStatement selectBrandID = c.prepareStatement("SELECT id FROM brand WHERE name = ?");
                selectBrandID.setString(1,brand.getName());
                ResultSet resultsBrand = selectBrandID.executeQuery();

                if (resultsBrand.next()) {
                    brandID = resultsBrand.getInt("id");
                }

                for(String product : brand.getProducts()){
                    int productID = 0;

                    PreparedStatement selectProductID = c.prepareStatement("SELECT id FROM producttype WHERE name = ?");
                    selectProductID.setString(1,product);
                    ResultSet resultsProduct = selectProductID.executeQuery();

                    if (resultsProduct.next()){
                        productID = resultsProduct.getInt("id");
                    }

                    PreparedStatement insertIntoJunction = c.prepareStatement("INSERT INTO brandproducttypejunction(brandid,productid) VALUES(?,?);");
                    insertIntoJunction.setInt(1,brandID);
                    insertIntoJunction.setInt(2,productID);
                    insertIntoJunction.execute();
                }
            }
            c.commit();
        }
        catch (Exception e) {
            rollback();
            System.out.println(e);
        }
    }

    @Override
    public void databaseIndexer() {
        setAutoCommit(false);
        try {
            PreparedStatement indexDatabase = c.prepareStatement("create index on producttype(name); " + "create index on brand(name); " + "create index on BrandProductTypeJunction(brandid, productid);");
            indexDatabase.execute();
            c.commit();
        }  catch (SQLException e) {
            rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void seedDatabase() {
        // Get brands from file
        List<Brand> brands = jsonService.deserializeBrand();
        addOrUpdateBrands(brands);
        databaseIndexer();
    }

    private void rollback() {
        try {
            c.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setAutoCommit(Boolean set) {
        try {
            c.setAutoCommit(set);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}