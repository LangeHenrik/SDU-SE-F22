package dk.sdu.se_f22.brandmodule.management.persistence;
import dk.sdu.se_f22.brandmodule.infrastructure.BrandInfrastructure;
import dk.sdu.se_f22.brandmodule.infrastructure.BrandInfrastructureInterface;
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
    private IJsonService jsonService = null;
    public BrandInfrastructureInterface BIM2 = null;

    public Persistence() {
        //Connect to database
        jsonService = new JsonService();
        BIM2 = new BrandInfrastructure();
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

        try(var c = DBConnection.getPooledConnection()) {
            List<Integer> brandIdList = new ArrayList();

            PreparedStatement getBrandId = c.prepareStatement("SELECT id FROM brand");
            ResultSet r = getBrandId.executeQuery();

            while(r.next()){
                brandIdList.add(r.getInt(0));
            }

            for (int i : brandIdList ){
                brandList.add(getBrand(i));
            }

        } catch (SQLException e) {

        }

        return brandList;
    }

    private Brand brandGetter(String name, Integer id) {
        Brand brand = null;
        try(var c = DBConnection.getPooledConnection()) {
            c.setAutoCommit(false);
            var stmt = c.createStatement();

            ResultSet r;
            PreparedStatement getBrand;
            if(name == null) {
                getBrand = c.prepareStatement("select brand.id, brand.name, brand.description, brand.founded, brand.headquarters, producttype.type from brandproducttypejunction as bpj " +
                        "right join brand on bpj.brandid = brand.id " +
                        "left join producttype on bpj.productid = producttype.id " +
                        "where brandid = ?;");

                getBrand.setInt(1,id);
                r = getBrand.executeQuery();
            } else{
                getBrand = c.prepareStatement("select brand.id, brand.name, brand.description, brand.founded, brand.headquarters, producttype.type from brandproducttypejunction as bpj " +
                        "right join brand on bpj.brandid = brand.id " +
                        "left join producttype on bpj.productid = producttype.id " +
                        "where brand.name = ?");

                getBrand.setString(1,name);
                r = getBrand.executeQuery();
            }

            Set<String> productSet = new HashSet<>();

            while (r.next()){
                brand = new Brand(
                        r.getInt("id"),
                        r.getString("name"),
                        r.getString("description"),
                        r.getString("founded"),
                        r.getString("headquarters")
                );

                productSet.add(r.getString(6));
            }

            ArrayList<String> productList = new ArrayList<String>(productSet);

            if (!productList.isEmpty())
                brand.setProducts(productList);

            stmt.close();
        } catch (SQLException e) {
            rollback();
        }

        return brand;
    }

    @Override
    public boolean deleteBrand(Brand brand) {
        return deleteBrand(brand.getId());
    }

    @Override
    public boolean deleteBrand(int id) {
        try(var c = DBConnection.getPooledConnection()) {
            c.setAutoCommit(false);
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
            return false;
        }

        return true;
    }

    @Override
    public boolean addOrUpdateBrands(List<Brand> brands) {
        try(var c = DBConnection.getPooledConnection()) {
            c.setAutoCommit(false);
            /* ------ Insert all products ------ */
            // Keep no duplicate products
            Set<String> products = new HashSet<>();
            for (var brand : brands) {
                products.addAll(brand.getProducts());
            }

            // Insert products into database
            for (var product : products) {
                PreparedStatement insertAllProducts = c.prepareStatement("INSERT INTO producttype (type) VALUES (?) ON CONFLICT(type) DO UPDATE SET type = EXCLUDED.type;");

                insertAllProducts.setString(1, product);
                insertAllProducts.execute();
            }

            /* ------ Insert all brands ------ */
            for (var brand : brands) {
                PreparedStatement insertAllBrands;

                // If id is set, update the brand in the database
                if (brand.getId() == null) {
                    insertAllBrands = c.prepareStatement("INSERT INTO brand (name, description, founded, headquarters) VALUES (?,?,?,?) ON CONFLICT(name) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, founded = EXCLUDED.founded, headquarters = EXCLUDED.headquarters;");
                }
                else {
                    insertAllBrands = c.prepareStatement("INSERT INTO brand (name, description, founded, headquarters, id) VALUES (?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, founded = EXCLUDED.founded, headquarters = EXCLUDED.headquarters;");
                    insertAllBrands.setInt(5, brand.getId());
                }

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

                    PreparedStatement selectProductID = c.prepareStatement("SELECT id FROM producttype WHERE type = ?");
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
            return false;
        }

        //Ensure that brands are indexed when loaded or changed
        //Talk to BIM2 to make this work
        //BIM2.indexBrands(getAllBrands());

        return true;
    }

    @Override
    public boolean databaseIndexer() {
        try(var c = DBConnection.getPooledConnection()) {
            c.setAutoCommit(false);
            PreparedStatement indexDatabase = c.prepareStatement("create index on producttype(type); " + "create index on brand(name); " + "create index on BrandProductTypeJunction(brandid, productid);");
            indexDatabase.execute();
            c.commit();
        }  catch (SQLException e) {
            rollback();
            return false;
        }

        return true;
    }

    @Override
    public void seedDatabase() {
        // Get brands from file
        List<Brand> brands = jsonService.deserializeBrand();
        addOrUpdateBrands(brands);
        databaseIndexer();
    }

    private void rollback() {
        try(var c = DBConnection.getPooledConnection()) {
            c.rollback();
        } catch (SQLException e) {

        }
    }

    public void setIndexingInterval(int indexingInterval)  {

        ResultSet r = null;
        PreparedStatement indexInterval = null;
        try(var c = DBConnection.getPooledConnection()) {
            indexInterval = c.prepareStatement("update config set brandindexinterval = ? ");
            indexInterval.setInt(1,indexingInterval);
            indexInterval.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    public int getIndexingInterval(){
        ResultSet r = null;
        PreparedStatement indexInterval = null;
        try(var c = DBConnection.getPooledConnection()) {
            indexInterval = c.prepareStatement("select brandindexinterval from config where id = 60");
            r = indexInterval.executeQuery();
            r.next();
            return r.getInt("brandindexinterval");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}