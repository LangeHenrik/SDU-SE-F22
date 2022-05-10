package dk.sdu.se_f22.contentmodule.index.crud.GruSearch;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class tagsTable implements Comparable<tagsTable>{
    private int id;
    private int usage;
    private tagsTable(int id){
        this.id = id;
        usage = 1;
    }

    void addUsage(){
        usage++;
    }

    int getUsage(){return usage;}

    int getId(){return id;}

    public int compareTo(tagsTable o) {
        if(this.getUsage() == o.getUsage()){
            return 0;
        }else if(this.getUsage() > o.getUsage()){
            return -1;
        }else {
            return 1;
        }
    }

    static ArrayList<tagsTable> TagIndexes(ArrayList<Integer> wordIDs, Database db){
        String SQL;
        ArrayList<Integer> tagId = new ArrayList<>();
        HashMap<Integer, tagsTable> sorted = new HashMap<>();

        for (int wordId: wordIDs){
            SQL = "SELECT page_id_ref FROM tags WHERE tags LIKE '% "+wordId+",%'";
            tagId = db.executeQueryArray(SQL);

            for (int ids : tagId){
                try {
                    sorted.get(ids).addUsage();
                }catch (Exception e){
                    sorted.put(ids, new tagsTable(ids));
                }
            }

        }
        ArrayList<tagsTable> sortArrayList = new ArrayList<>();
        sortArrayList.addAll(sorted.values());
        Collections.sort(sortArrayList);
        return sortArrayList;
    }

}
