package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

public class SortingModuleImpl implements SortingModule {

    private SearchQuery query;
    private String searchString;

    public SortingModuleImpl() {

    }

    @Override
    public void searchString(String searchString) {
        this.searchString = searchString;
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
    public void addRange(int rangeId, String startRange, String endRange) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clearRange() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPagination(int page, int pageSize) {
        this.query.setPagination(page, pageSize);
    }

    @Override
    public void setScoring(String scoring) {
        // TODO Auto-generated method stub
        
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
