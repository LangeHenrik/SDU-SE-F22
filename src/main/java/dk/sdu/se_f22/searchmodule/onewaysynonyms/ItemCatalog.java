package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.util.LinkedList;

public class ItemCatalog {
    //Attributes
    private LinkedList<Item> catalog;

    //Constructor
    public ItemCatalog(){
        this.catalog = new LinkedList<>();
        addSubItems();
    }

    //Methods
    public void addITem(Item item){
        catalog.add(item);
    }

    public boolean removeItem(Item item){
        return catalog.remove(item);
    }

    private void addSubItems(){
        for(Item item : catalog){
            item.getSuperItem().AddSubItem(item);
        }
    }

    public LinkedList<Item> oneWaySynonymStrings(String string) throws notFoundException {
        for(Item item : catalog) {
            if(item.getName().equals(string)){
                return item.getSubItems();
            }
        }
        throw new notFoundException("Item not found in database.");
    }
}
