package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.notFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI.deleteItems;
import static dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI.updateSuperId;

public class OneWayImplementation implements OneWayInterface {
    private ItemCatalog itemCatalog;

    public OneWayImplementation() {
        this.itemCatalog = new ItemCatalog(getDatabaseItems());
        DatabaseAPI.initializeTable();
    }

    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) throws NullPointerException {
        int length = tokens.size();
        LinkedList<Item> items;
        for (int i = 0; i < length; i++) {
            try {
                items = (this.itemCatalog.oneWaySynonymStrings(tokens.get(i)));

                for (Item item : items) {
                    if (!tokens.contains(item.getName())){
                        tokens.add(item.getName());
                    }
                }
            } catch (notFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return tokens;
    }

    public Item[] getDatabaseItems() {
        Item[] arr = DatabaseAPI.readEntireDB();
        if (arr == null) {
            System.out.println("Could not read from the database");
            return null;
        }
        System.out.println("The read was a succes");
        return arr;
    }


    @Override
    public void addItem(String name, String superItemName) {
        int idSuperItem = validateName(superItemName);
        addItem(name, idSuperItem);
    }

    public void addItem(String name, int superItemID) {
        if (isNumber(name) || name.equalsIgnoreCase("root")) {
            System.out.println("Invalid name");
            return;
        } else {
            if (validateID(superItemID)) {
                if (DatabaseAPI.addItem(name, superItemID)) {
                    System.out.println("Transaction was a succes");
                    System.out.println("Item: " + name + " with super ID: " + superItemID + " was added");
                    return;
                }
            }
        }
        System.out.println("Could not add " + name + " with super ID: " + superItemID);
    }

    @Override
    public void changeSuperId(String itemName, String SuperItemName) {
        changeSuperId(validateName(itemName), validateName(SuperItemName));

    }

    public void changeSuperId(String itemName, int SuperItemID) {
        changeSuperId(validateName(itemName), SuperItemID);
    }

    public void changeSuperId(int itemId, String SuperItemName) {
        changeSuperId(itemId, validateName(SuperItemName));
    }

    public void changeSuperId(int itemId, int SuperItemId) {
        if (validateID(itemId) && itemId > 0 && validateID(SuperItemId)) {
            if (itemId == SuperItemId) {
                System.out.println("Cannot update super id to be same as item id");
            } else {
                DatabaseAPI.updateSuperId(itemId, SuperItemId);
                System.out.println("Transaction was a succes");
                System.out.println("Updated Super ID");
            }
        } else System.out.println("Could not update Super ID");


    }

    @Override
    public void changeItemName(String oldName, String newName) {
        int id = validateName(oldName);
        changeItemName(id, newName);
    }

    public void changeItemName(int oldItemId, String newName) {
        if (validateID(oldItemId)) {
            if (oldItemId > 0 && !newName.equalsIgnoreCase("root") && !isNumber(newName)) {
                if (DatabaseAPI.updateName(oldItemId, newName)) {
                    System.out.println("Transaction was a succes");
                    System.out.println("Successfully updated item name");
                    return;
                }
            }
        }
        System.out.println("Unable to update item name");
    }

    @Override
    public void deleteItem(String name) {
        deleteItem(validateName(name));
    }

    public void deleteItem(int id) {
        Item item;
        LinkedList<Item> subItems;
        if (id > 0) {
            item = itemCatalog.getItemInCatalog(id);
            if (item == null) {
                System.out.println("Couldn't find item");
                return;
            } else {
                subItems = item.getSubItems();
                for (Item subItem : subItems) {
                    subItem.setSuperItem(item.getSuperItem());
                    subItem.setSuperId(item.getSuperId());
                    item.getSuperItem().removeSubItem(item);
                    updateSuperId(subItem.getId(), item.getSuperId());
                }
                if (deleteItems(item.getId())) {
                    System.out.println("Transaction was a succes");
                    System.out.println("Item successfully deleted");
                    return;
                }
            }
        }
        System.out.println("Unable to delete item");
    }

    @Override
    public void printCatalog() {
        Item[] content = DatabaseAPI.readEntireDB();
        if (content != null) {
            for (Item item : content) {
                System.out.println(item);
            }
        }
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateID(int input) {
        int amount = itemCatalog.containsItem(input);

        return amount == 1 && input >= 0;
    }

    private int validateName(String name) {
        int id, amount;
        id = DatabaseAPI.searchBasedOnName(name);
        amount = itemCatalog.containsItem(name);

        if (amount == 1 && id >= 0) {
            return id;
        } else if (amount > 1) {
            return -1;
        } else return -2;
    }
}
