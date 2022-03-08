package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryTest {

    @Test
    void setCategoryTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(7);
        s.setCategory(a);
        assertEquals(a, s);
    }

    @Test
    void addCategoryTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        s.addCategory(2);
        assertEquals(a, s);
    }

    @Test
    void clearCategoryTest() {
    }

    @Test
    void addRangeTest() {

    }

    @Test
    void clearRangeTest() {
    }

    @Test
    void setPaginationTest() {
    }

    @Test
    void setScoringTest() {
        SearchQuery s = new SearchQuery();
        s.setScoring(5);
        assertEquals(5, s.scoring);
    }
}