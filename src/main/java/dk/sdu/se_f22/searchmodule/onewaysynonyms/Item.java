package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.util.LinkedList;

public class Item {
    //Attributes
    private String name;
    private Item superItem;
    private LinkedList<Item> subItems;

    //Constructor
    public Item(String name){
        this.name = name;
    }
    public Item(String name, Item superItem){
        this(name);
        this.superItem = superItem;
    }

    //Methods
    public void AddSubItem(Item subItem){
        subItems.add(subItem);
    }
    public void setSuperItem(Item superItem) {
        this.superItem = superItem;
    }
    public Boolean removeSubItem(Item item){
        return subItems.remove(item);
    }
}
