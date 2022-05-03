package dk.sdu.se_f22.contentmodule.infrastructure.data;


import java.sql.*;
import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

public class Database implements DatabaseInterface {
    public void saveFilteredTokens(ArrayList<String> tokens) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ArrayList<String> loadFilteredTokens() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void printTokens(ArrayList<String> tokens) {
        for (int i = 0; i < tokens.toArray().length; i++) {
            System.out.println(tokens.get(i));
        }
    }

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
}
