package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.util.*;


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
        HashMap<Integer, String[]> h = new HashMap<>();
        h.put(1, new String[]{"3", "8"});
        h.put(2, new String[]{"19.4", "52.7"});


        SearchQuery s = new SearchQuery();
        s.addRange(1, "3", "8");
        s.addRange(2, "19.4", "52.7");

        Iterator<Map.Entry<Integer, String[]>> iteratorH = h.entrySet().iterator();
        Iterator<Map.Entry<Integer, String[]>> iteratorS = s.getRange().entrySet().iterator();

        while (iteratorH.hasNext()) {
            Map.Entry<Integer, String[]> temp_H = iteratorH.next();
            Map.Entry<Integer, String[]> temp_S = iteratorS.next();

            if (!temp_H.getKey().equals(temp_S.getKey()) &&
                    !Arrays.toString(temp_H.getValue()).equals(Arrays.toString(temp_S.getValue()))) {
                fail("addRangeTest failure.");
            }
        }
    }

    @Test
    void clearRangeTest() {
        SearchQuery s = new SearchQuery();
        HashMap<Integer, String[]> h = new HashMap<>();

        s.addRange(1, "3", "8");
        s.addRange(2, "19.4", "52.7");
        s.clearRange();

        assertEquals(h, s.getRange());
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