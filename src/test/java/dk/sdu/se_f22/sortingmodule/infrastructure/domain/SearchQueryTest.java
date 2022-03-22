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
        assertEquals(a, s.category);
    }

    @Test
    void addCategoryTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        s.addCategory(2);
        assertEquals(a, s.category);
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
        SearchQuery s = new SearchQuery();
        int[] values = {1,2};
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.pagination);

        values[0] = 654;
        values[1] = 68469826;
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.pagination);

        values[0] = -48;
        values[1] = 867;
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.pagination);
    }

    @Test
    void setScoringTest() {
        SearchQuery s = new SearchQuery();
        s.setScoring(5);
        assertEquals(5, s.scoring);
    }
}