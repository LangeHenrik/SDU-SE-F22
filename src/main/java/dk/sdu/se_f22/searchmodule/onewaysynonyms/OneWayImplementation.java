package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OneWayImplementation implements OneWayInterface {

    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        return null;
    }

    public Item[] createItemCatalog() {
        return DatabaseAPI.readEntireDB();
    }


    @Override
    public void createItem() {
        try {
            DatabaseAPI.addItem("Cake");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeItemPlacement() {
        try {
            DatabaseAPI.updateSuperId("Cake", 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showCatalog() {
        Item[] content = DatabaseAPI.readEntireDB();
        for (Item item:content) {
            System.out.println(item);
        }
    }

    @Override
    public void changeItemName() {
        try {
            DatabaseAPI.updateName(1, "Football");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnNameList() {

    }
}
