package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.util.*;

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

    private HashMap<Integer, Integer> fillNumberInEachGeneration(Item root){
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0,1);
        LinkedList<Item> childrenInGeneration = new LinkedList<>();
        childrenInGeneration.add(root);
        LinkedList<Item> nextGeneration = new LinkedList<>();
        int cycle = 1;
        while (true){
            for(Item item : childrenInGeneration){
                try{
                    nextGeneration.addAll(item.getSubItems());
                }catch (NullPointerException ex){
                }
            }
            if(nextGeneration.size() == 0){
                break;
            }
            map.put(cycle,nextGeneration.size());
            cycle++;
            childrenInGeneration.clear();
            childrenInGeneration.addAll(nextGeneration);
            nextGeneration.clear();
        }
        return map;
    }
   
    public void drawImage(Item item){
        HashMap<Integer, Integer> numberInEachGeneration = fillNumberInEachGeneration(item);

        int width = imageWidth(numberInEachGeneration.keySet());
        int length = imageLength(numberInEachGeneration.values());


    }

    private int imageLength(Collection<Integer> collection){
        int biggest = 0;
        for(int i : collection){
            if(i > biggest){
                biggest = i;
            }
        }
        return biggest * 110;
    }
    private int imageWidth(Set<Integer> set){
        return set.size() * 150;
    }

    public static void main(String[] args) {

    }

}
