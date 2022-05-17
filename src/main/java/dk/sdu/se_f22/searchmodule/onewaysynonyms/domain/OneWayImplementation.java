package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.OneWayInterface;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.notFoundException;

import java.util.LinkedList;
import java.util.List;

public class OneWayImplementation implements OneWayInterface {

    public List<String> filter(List<String> tokens) {
        ItemCatalog itemCatalog = new ItemCatalog(createItemArray());
        int length = tokens.size();
        for (int i = 0; i < length; i++) {
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
        tokens.add("motordrevet");
        OneWayImplementation owi = new OneWayImplementation();
        tokens = owi.filter(tokens);
        for (String items : tokens) {
            System.out.println(items + "\n");
        }
    }

    @Override
    public void createItem() {
        DatabaseAPI.addItem("Cake");
    }

    @Override
    public void changeItemPlacement() {
        DatabaseAPI.updateSuperId("Cake", 2);
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
        DatabaseAPI.updateName(1, "Football");
    }

    @Override
    public void returnNameList() {

    }
}
