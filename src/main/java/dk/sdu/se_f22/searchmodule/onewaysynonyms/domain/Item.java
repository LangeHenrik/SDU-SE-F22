package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import java.util.LinkedList;

public class Item {
    //Attributes
    private String name;
    private Item superItem;
    private int id;
    private int superId;
    private LinkedList<Item> subItems;

    //Constructor
    public Item(String name){
        this.name = name;
        this.subItems = new LinkedList<>();
    }
    public Item(int id,String name, int superId){
        this.name = name;
        this.id = id;
        this.superId = superId;
        this.subItems = new LinkedList<>();
    }

    public Item(String name, Item superItem){
        this(name);
        this.superItem = superItem;
    }

    //Methods
    public void addSubItem(Item subItem){
        subItems.add(subItem);
    }
    public void setSuperItem(Item superItem) {
        this.superItem = superItem;
    }
    public void removeSubItem(Item item){
        subItems.remove(item);
    }
    public String getName() {
        return name;
    }
    public LinkedList<Item> getSubItems(){
        return this.subItems;
    }
    public Item getSuperItem() {
        return superItem;
    }
    public int getSuperId() {
        return superId;
    }
    public void setSuperId(int id){
        this.superId = id;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString(){
        return "Id: "+getId()+" Name: "+getName()+" SuperId: "+getSuperId();
    }
}
