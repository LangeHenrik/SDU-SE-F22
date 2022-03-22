package dk.sdu.se_f22.sortingmodule.infrastructure.data;

import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SearchQuery;

import java.util.ArrayList;

public class SaveSearchQuery {
    public static void saveSearch(SearchQuery query) {
        int pageNumber = query.getPagination()[0];
        int pageSize = query.getPagination()[1];
        ArrayList<Object> queryRanges = query.getRange();
        ArrayList<Integer> queryCategories = query.getCategory();
        int queryScoring = query.getScoring();
    }
}