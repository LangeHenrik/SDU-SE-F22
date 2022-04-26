package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OneWayImplementation implements OneWayInterface {

    public List<String> filter(List<String> tokens) {
        ItemCatalog itemCatalog = new ItemCatalog(createItemArray());
        int length = tokens.size();
        for (int i = 0; i<length; i++) {
            LinkedList<Item> items = null;
            try {
                items = (itemCatalog.oneWaySynonymStrings(tokens.get(i)));
                for (Item item : items) {
                    tokens.add(item.getName());
                }
            } catch (notFoundException e) {
                System.out.println("Token doesn't exist in the database");
            }
        }
        return tokens;
    }

    public Item[] createItemArray() {
        return DatabaseAPI.readEntireDB();
    }


    public static void main(String[] args) {
        List<String> tokens = new LinkedList<>();
        tokens.add("Humans");
        OneWayImplementation owi = new OneWayImplementation();
        tokens = owi.filter(tokens);
        for (Item items : owi.createItemArray()){
            System.out.println(items+"\n");
        }
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
        for (Item item : content) {
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
