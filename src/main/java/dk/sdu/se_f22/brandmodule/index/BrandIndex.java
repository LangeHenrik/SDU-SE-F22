package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.List;

public class BrandIndex implements IndexInterface {

    private Connection DBConn = DBConnection.getConnection();

    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        List<Brand> brandList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        String finalQuery = "SELECT * FROM TokenBrandMap LEFT JOIN Brand ON Brand.id = brandId WHERE tokenId = ?";

        try {
            for (int i = 0; i < tokens.size(); i++) {
                PreparedStatement queryStatement = DBConn.prepareStatement("SELECT id FROM Tokens WHERE token = ?");
                queryStatement.setString(1, tokens.get(i));
                ResultSet queryResultSet = queryStatement.executeQuery();
                tempList.add(Integer.valueOf(queryResultSet.getString("id")));

                queryResultSet.close();
                queryStatement.close();
            }

            for (int i = 0; i < tempList.size(); i++) {
                finalQuery += " AND token = ? ";
            }

            PreparedStatement queryStatement = DBConn.prepareStatement(finalQuery);

            for (int i = 0; i < tempList.size(); i++) {
                queryStatement.setString(i + 1, String.valueOf(tempList.get(i)));
            }

            ResultSet queryResultSet = queryStatement.executeQuery();

            while (queryResultSet.next()) {
                brandList.add(new Brand(Integer.valueOf(queryResultSet.getString("id")),
                        queryResultSet.getString("name"),
                        queryResultSet.getString("description"),
                        queryResultSet.getString("founded"),
                        queryResultSet.getString("headquarters"), new ArrayList<String>()));
            }
            queryResultSet.close();
            queryStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {
        try {
            PreparedStatement mapInsert = DBConn.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?)");
            PreparedStatement tokenInsert = DBConn.prepareStatement("INSERT INTO tokens(token) VALUES (?)");
            PreparedStatement queryToken = DBConn.prepareStatement("SELECT token FROM tokens");

            ResultSet rs = queryToken.executeQuery();

            ArrayList<String> newTokens = new ArrayList<>(tokens);

            String dbColumnToken = "token"; //

            System.out.println(!rs.next() && newTokens.get(0) != null);
             if (!rs.next() && newTokens.get(0) != null) {
                 tokenInsert.setString(1, newTokens.get(0));
                 tokenInsert.execute();
                 rs = queryToken.executeQuery();
                 System.out.println(newTokens.get(0)+" has been added.");
             }

            System.out.println(tokens.size()+" is tokens.size.");
            System.out.println(rs.first());
            while (rs.next()) {
                for (int i = 0; i < tokens.size(); i++) {
                    System.out.println(rs.getString("token") + " is rs.getString. POG POG "+newTokens.get(i)+" is newTokens.get(i)");
                    if (!(rs.getString("token").equals(newTokens.get(i)))) {
                        tokenInsert.setString(1, newTokens.get(i));
                        tokenInsert.execute();
                        System.out.println(newTokens.get(i) + " has been added. INSIDE IF");
                    }
                    else { continue; }

                    PreparedStatement queryTokenId = DBConn.prepareStatement("SELECT id FROM tokens where token = ?");
                    queryTokenId.setString(1, newTokens.get(i));
                    ResultSet rsTokenId = queryTokenId.executeQuery();
                    while (rsTokenId.next()) {
                        mapInsert.setInt(1, brand.getId());
                        mapInsert.setInt(2, rsTokenId.getInt(1));
                        mapInsert.execute();
                    }
                    rsTokenId.close();
                    queryTokenId.close();
                }
                rs = queryToken.executeQuery();
            }

            //preparedStatements
            mapInsert.close();
            tokenInsert.close();
            queryToken.close();

            //resultSets
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

