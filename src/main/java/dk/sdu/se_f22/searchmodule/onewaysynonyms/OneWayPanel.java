package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class OneWayPanel extends JPanel {

    private Item root;
    private int width;
    private int height;
    private HashMap<Integer,LinkedList<Item>> tree;

    public OneWayPanel(Item item){
        HashMap<Integer, LinkedList<Item>> tree = fillNumberInEachGeneration(item);
        int height = tree.size()*100;
        int width = imageLength(tree);

        this.root = item;
        this.height = height;
        this.width = width;
        this.tree = tree;
    }

    private HashMap<Integer, LinkedList<Item>> fillNumberInEachGeneration(Item root){
        HashMap<Integer, LinkedList<Item>> map = new HashMap<>();


        LinkedList<Item> childrenInGeneration = new LinkedList<>();
        childrenInGeneration.add(root);
        map.put(0, (LinkedList<Item>) childrenInGeneration.clone());
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
            map.put(cycle, (LinkedList<Item>) nextGeneration.clone());
            cycle++;
            childrenInGeneration.clear();
            childrenInGeneration.addAll((Collection<? extends Item>) nextGeneration.clone());
            nextGeneration.clear();
        }
        return map;
    }

    private int imageLength(HashMap<Integer, LinkedList<Item>> list){
        int biggest = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).size() > biggest){
                biggest = list.get(i).size();
            }
        }
        return biggest*150;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(Color.getHSBColor(342, 100, (float) 67.45));
        int number = 0;
        HashMap<String,Integer[]> log = new HashMap<>();
        int x2 = 0;
        int y2 = 0;
        for(int i = 0; i< tree.size(); i++){
            for(Item item : tree.get(number)){
                log.put(item.getName(), new Integer[]{getX(number,item)+10,i*100});
                if(log.containsKey(getSuperName(item))){
                    x2 = log.get(getSuperName(item))[0]+50;
                    y2 = log.get(getSuperName(item))[1]+50;
                    g2D.drawLine(getX(number,item)+60,i*100+25,x2,y2);
                }
                g2D.fillRoundRect(getX(number, item)+10,i*100,100,50,20,20);
                g2D.setColor(Color.black);
                g2D.drawString(item.getName(),getX(number, item)+15,i*100+25);
                g2D.setColor(Color.getHSBColor(342, 100, (float) 67.45));
            }
           number++;
        }
    }

    private String getSuperName(Item item) {
        if(item.getSuperItem() == null){
            return "tehe";
        }
        return item.getSuperItem().getName();
    }

    private int getX(int number, Item item) {
        return this.width / tree.get(number).size() * tree.get(number).indexOf(item);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
