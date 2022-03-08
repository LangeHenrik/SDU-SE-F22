package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchQuery {
    String query;
    int[] pagination = new int[2];
    ArrayList<Object> range = new ArrayList<>();
    ArrayList<Integer> category = new ArrayList<>();

    public void setCategory(ArrayList<Integer> categories) {
        // TODO Auto-generated method stub
        
    }

    public void addCategory(int category) {
        // TODO Auto-generated method stub
        
    }

    public void clearCategory() {
        // TODO Auto-generated method stub
        
    }

    public void addRange(int rangeId, String startRange, String endRange) {
        //range.add(Range(rangeId, new String[]{startRange, endRange}));
        // CODE DOESN'T WORK, CHANGE RANGE ATTRIBUTE AND OBJECT NAME WHEN AVAILABLE
    }

    public void clearRange() {
        // TODO Auto-generated method stub
        
    }

    public void setPagination(int page, int pageSize) {
        // TODO Auto-generated method stub
        
    }

    public void setScoring(String scoring) {
        // TODO Auto-generated method stub
        
    }

}
