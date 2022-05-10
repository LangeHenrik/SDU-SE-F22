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
        HashMap<Integer, Long[]> h = new HashMap<>();
        h.put(1, new Long[]{3L, 8L});
        h.put(2, new Long[]{7L, 52L});


        SearchQuery s = new SearchQuery();
        s.addRangeLong(1, 3, 8);
        s.addRangeDouble(2, 19.4, 52.7);

        Iterator<Map.Entry<Integer, Long[]>> iteratorH = h.entrySet().iterator();
        Iterator<Map.Entry<Integer, Long[]>> iteratorS = s.getRangeLong().entrySet().iterator();

        while (iteratorH.hasNext()) {
            Map.Entry<Integer, Long[]> temp_H = iteratorH.next();
            Map.Entry<Integer, Long[]> temp_S = iteratorS.next();

            if (!temp_H.getKey().equals(temp_S.getKey()) &&
                    !Arrays.equals((temp_H.getValue()), (temp_S.getValue()))) {
                fail("addRangeTest failure.");
            }
        }
    }

    @Test
    void clearRangeTest() {
        SearchQuery s = new SearchQuery();
        HashMap<Integer, Long[]> h = new HashMap<>();

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