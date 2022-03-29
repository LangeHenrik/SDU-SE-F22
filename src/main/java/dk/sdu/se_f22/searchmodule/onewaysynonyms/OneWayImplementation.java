package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OneWayImplementation implements OneWayInterface {

    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        return null;
    }

    public Item[] createItemCatalog() {
        ArrayList<Item> items = new ArrayList<Item>();
        ResultSet content = DatabaseAPI.read();
        try {
            while (content.next()) {
                items.add(new Item(content.getInt(1),content.getString(2),content.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Item item:items) {
            if (item.getSuperId()!=0){
                for (Item item2:items){
                    if (item2.getId()==item.getSuperId())
                    item.setSuperItem(item2);
                }
            }
        }
        Item[] item = items.toArray(new Item[items.size()]);
        return item;
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
        ResultSet content = DatabaseAPI.read();
        try {
            while (content.next()) {
                System.out.println(content.getInt(1) + "-" + content.getString(2) + "-" + content.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
