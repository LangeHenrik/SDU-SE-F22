package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;

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
        this.query.setCategory(categories);
        
    }

    @Override
    public void addCategory(int category) {
        this.query.addCategory(category);
        
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
        this.query.setPagination(page, pageSize);
    }

    @Override
    public void setScoring(int scoring) {
        this.query.setScoring(scoring);
        
    }

    @Override
    public SearchHits search() {
        SearchModuleImpl searchModule = new SearchModuleImpl();
        searchModule.search(this.searchString);

        // TODO Auto-generated method stub
        return null;
    }
    
    private void saveSearch() {
        // TODO Create save to database
    }
}
