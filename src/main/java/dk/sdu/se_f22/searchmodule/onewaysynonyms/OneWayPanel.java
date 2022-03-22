package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class OneWayPanel extends JPanel {

    private Item root;
    private int width;
    private int length;
    private HashMap<Integer,Integer> numberInGeneration;

    public OneWayPanel(Item item){
        HashMap<Integer, Integer> numberInEachGeneration = fillNumberInEachGeneration(item);
        int width = numberInEachGeneration.size()*200;
        int length = imageLength(numberInEachGeneration.values());
        this.setPreferredSize(new Dimension(width,length));

        this.root = item;
        this.length = length;
        this.width = width;
        this.numberInGeneration = numberInEachGeneration;
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
                }catch (NullPointerException ex){}
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

    private int imageLength(Collection<Integer> collection){
        int biggest = 0;
        for(int i : collection){
            if(i > biggest){
                biggest = i;
            }
        }
        return biggest * 110;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(this.width/2,10,100,50,20,20);
        g2D.drawString(root.getName(),this.width/2-100,60);
        int number = 0;

        for(int i = 1; i<numberInGeneration.size(); i++){
           number = numberInGeneration.get(i);
           for(int j = 0; j<number; j++){
               g2D.drawRoundRect(this.width/number+110*j,i*150,100,50,20,20);
           }
        }

    }
}
