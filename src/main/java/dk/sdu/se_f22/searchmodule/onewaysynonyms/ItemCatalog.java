package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.util.LinkedList;
import java.util.List;

public class ItemCatalog {
    //Attributes
    private LinkedList<Item> catalog;

    //Constructor
    public ItemCatalog(Item[] items){
        this.catalog = new LinkedList<>();
        this.catalog.addAll(List.of(items));
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
            try{
                item.getSuperItem().AddSubItem(item);
            } catch (NullPointerException ex){

            }
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
