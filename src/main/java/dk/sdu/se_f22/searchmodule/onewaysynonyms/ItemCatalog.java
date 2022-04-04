package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javax.swing.*;

import java.util.*;
import java.util.List;

public class ItemCatalog{
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

    public LinkedList<Item> getCatalog() {
        return catalog;
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

    public static void main(String[] args) {
        Item i1 = new Item("køretøjer");
        Item i2 = new Item("motordrevet",i1);
        Item i3 = new Item("menneskedrevet",i1);
        Item i4 = new Item("racerbil",i2);
        Item i5 = new Item("personbil",i2);
        Item i6 = new Item("lastbil",i2);
        Item i7 = new Item("superbil",i5);
        Item i8 = new Item("5 dørs",i5);
        Item i9 = new Item("3 dørs",i5);
        Item i10 = new Item("børnecontainer",i8);
        Item i11 = new Item("lukus",i8);
        Item i12 = new Item("cykel",i3);
        Item i13 = new Item("løbehjul",i3);
        Item i14 = new Item("skateboard",i3);

        Item[] list = new Item[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14};
        ItemCatalog test = new ItemCatalog(list);
        


    }

}
