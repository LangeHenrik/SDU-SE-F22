package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandIndex implements IndexInterface {

    private Connection DBConn = DBConnection.getConnection();

    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        List<Brand> brandList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        String finalQuery = "SELECT * FROM TokenBrandMap LEFT JOIN Brand ON Brand.id = brandId WHERE tokenId = ?";

        try {
            for(int i = 0; i < tokens.size(); i++) {
                PreparedStatement queryStatement = DBConn.prepareStatement("SELECT id FROM Tokens WHERE token = ?");
                queryStatement.setString(1, tokens.get(i));
                ResultSet queryResultSet = queryStatement.executeQuery();
                tempList.add(Integer.valueOf(queryResultSet.getString("id")));
            }

            for (int i = 0; i < tempList.size(); i++) {
                finalQuery += "AND token = ?";
            }

            PreparedStatement queryStatement =DBConn.prepareStatement(finalQuery);

            for (int i = 0; i < tempList.size(); i++) {
                queryStatement.setString(i+1, String.valueOf(tempList.get(i)));
            }

            ResultSet

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {

    }
}

