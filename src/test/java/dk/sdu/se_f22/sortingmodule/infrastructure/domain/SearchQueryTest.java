package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        s.addCategory(1);
        s.addCategory(2);
        s.clearCategory();
        assertEquals(a, s.category);
    }

    @Test
    void addRangeTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<String[]> a = new ArrayList<>();
        a.add(new String[]{"1", "3", "8"});
        a.add(new String[]{"2", "19.4", "52.7"});

        s.addRange("1", "3", "8");
        s.addRange("2", "19.4", "52.7");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).toString().equals(s.getRange().get(i).toString())) {
                fail();
            }
        }

    }

    @Test
    void clearRangeTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<String[]> a = new ArrayList<>();

        s.addRange("1", "3", "8");
        s.addRange("2", "19.4", "52.7");
        s.clearRange();

        assertEquals(a, s.getRange());
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