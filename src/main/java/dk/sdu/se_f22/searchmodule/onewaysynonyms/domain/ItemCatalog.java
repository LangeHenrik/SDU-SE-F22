package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.notFoundException;

import java.util.*;
import java.util.List;

public class ItemCatalog{
    //Attributes
    private LinkedList<Item> catalog;

    //Constructor
    public ItemCatalog(Item[] items){
        this.catalog = new LinkedList<>();
        this.catalog.addAll(List.of(items));
    }

    //Methods
    public void addITem(Item item){
        catalog.add(item);
    }

    public boolean removeItem(Item item){
        return catalog.remove(item);
    }

    public LinkedList<Item> getCatalog() {
        return catalog;
    }

    public int containsItem(String s){
        int count =0;
        for (Item item:catalog) {
            if (item.getName().equals(s))count++;
        }
        return count;
    }
    public int containsItem(int id){
        for (Item item:catalog) {
            if (item.getId() == id);
            return 1;
        }
        return 0;
    }

    public LinkedList<Item> oneWaySynonymStrings(String string) throws notFoundException {
        for(Item item : catalog) {
            if(item.getName().equals(string)){
                return item.getSubItems();
            }
        }
        throw new notFoundException("Item not found in database.");
    }

    public Item getItemInCatalog(int id){
        for (Item item:catalog) {
            if (item.getId()==id){
                return item;
            }
        }
        return null;
    }
}
