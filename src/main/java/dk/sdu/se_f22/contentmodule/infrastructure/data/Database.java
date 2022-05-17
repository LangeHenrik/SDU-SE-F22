package dk.sdu.se_f22.contentmodule.infrastructure.data;


import java.sql.*;
import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

public class Database implements DatabaseInterface {

    @Override
    public void createTable(String table_name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveTokens(String table_name, ArrayList<String> tokens) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<String> loadTokens(String table_name) {
        // TODO Auto-generated method stub
        return null;
    }
//
//
//
//    public void saveFilteredTokens(ArrayList<String> tokens) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    public ArrayList<String> loadFilteredTokens() {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    public void printTokens(ArrayList<String> tokens) {
//        for (int i = 0; i < tokens.toArray().length; i++) {
//            System.out.println(tokens.get(i));
//        }
//    }
//
//    @Override
//    public void createTable(String table_name) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    @Override
//    public void saveTokens(String table_name, ArrayList<String> tokens) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public ArrayList<String> loadTokens(String table_name) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//}
//
//    public void executeQuery(String query) {
//        PreparedStatement insertstatement;
//        try{
//            insertstatement=conn.prepareStatement(query);
//            insertstatement.execute();
//            insertstatement.close();
//        }catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//
//    public void logParameters(int pageid, int parameterid) {
//        Database database = Database.getInstance();
//        database.executeQuery("INSERT INTO cms_usedparameters (page_id, parameter_id) VALUES (" + pageid + ", " + parameterid + ")");
//    }
//
//    public String getParameters() {
//        PreparedStatement loadstatement;
//        String chars = "";
//        try {
//            loadstatement=conn.prepareStatement("SELECT * FROM cms_tokenparameters");
//            ResultSet queryResultSet = loadstatement.executeQuery();
//            while (queryResultSet.next()) {
//                chars = chars + queryResultSet.getString(("limitedchar"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return chars;
//    }
//
//    public int getPageID(int html_id) {
//        PreparedStatement loadstatement;
//        int pageid = 0;
//        try {
//            loadstatement=conn.prepareStatement("SELECT * FROM cms_htmlpages WHERE html_id = "+html_id+"");
//            ResultSet queryResultSet = loadstatement.executeQuery();
//            while (queryResultSet.next()) {
//                pageid = queryResultSet.getInt("page_id");
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return pageid;
//    }
//
//    public int getParameterID(char parameter) {
//        PreparedStatement loadstatement;
//        int parameterid = 0;
//        try {
//            loadstatement=conn.prepareStatement("SELECT * FROM cms_parameterslist WHERE parameter = '"+parameter+"'");
//            ResultSet queryResultSet = loadstatement.executeQuery();
//            while (queryResultSet.next()) {
//                parameterid = queryResultSet.getInt("parameter_id");
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return parameterid;
//    }
}
//
