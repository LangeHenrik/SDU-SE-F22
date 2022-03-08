package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

public class SortingModuleImpl implements SortingModule {

    private SearchQuery query;

    public SortingModuleImpl() {

    }

    @Override
    public void setCategory(ArrayList<Integer> categories) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addCategory(int category) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clearCategory() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addRange(int rangeId, double startRange, double endRange) {
        this.query.addRange(rangeId, startRange, endRange);
        
    }

    @Override
    public void clearRange() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPagination(int page, int pageSize) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setScoring(int scoring) {
        this.query.setScoring(scoring);
        
    }

    @Override
    public SearchHits search() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void saveSearch() {
        // TODO Create save to database
    }
}
