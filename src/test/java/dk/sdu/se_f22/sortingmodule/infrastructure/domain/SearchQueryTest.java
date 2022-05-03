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
        assertEquals(a, s.getCategory());
    }

    @Test
    void addCategoryTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        s.addCategory(2);
        assertEquals(a, s.getCategory());
    }

    @Test
    void clearCategoryTest() {
        SearchQuery s = new SearchQuery();
        ArrayList<Integer> a = new ArrayList<>();
        s.addCategory(1);
        s.addCategory(2);
        s.clearCategory();
        assertEquals(a, s.getCategory());
    }

    @Test
    void addRangeTest() {
        HashMap<Integer, long[]> h = new HashMap<>();
        h.put(1, new long[]{3, 8});
        h.put(2, new long[]{7, 52});


        SearchQuery s = new SearchQuery();
        s.addRangeLong(1, 3, 8);
        s.addRangeDouble(2, 19.4, 52.7);

        Iterator<Map.Entry<Integer, long[]>> iteratorH = h.entrySet().iterator();
        Iterator<Map.Entry<Integer, long[]>> iteratorS = s.getRangeLong().entrySet().iterator();

        while (iteratorH.hasNext()) {
            Map.Entry<Integer, long[]> temp_H = iteratorH.next();
            Map.Entry<Integer, long[]> temp_S = iteratorS.next();

            if (!temp_H.getKey().equals(temp_S.getKey()) &&
                    !Arrays.equals((temp_H.getValue()), (temp_S.getValue()))) {
                fail("addRangeTest failure.");
            }
        }
    }

    @Test
    void clearRangeTest() {
        SearchQuery s = new SearchQuery();
        HashMap<Integer, long[]> h = new HashMap<>();

        s.addRangeDouble(1, 3.9, 8.1);
        s.addRangeDouble(2, 19.4, 52.7);
        s.clearRangeDouble();

        assertEquals(h, s.getRangeDouble());
    }

    @Test
    void setPaginationTest() {
        SearchQuery s = new SearchQuery();
        int[] values = {1,2};
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.getPagination());

        values[0] = 654;
        values[1] = 68469826;
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.getPagination());

        values[0] = -48;
        values[1] = 867;
        s.setPagination(values[0], values[1]);
        assertArrayEquals(values, s.getPagination());
    }

    @Test
    void setScoringTest() {
        SearchQuery s = new SearchQuery();
        s.setScoring(5);
        assertEquals(5, s.getScoring());
    }
}