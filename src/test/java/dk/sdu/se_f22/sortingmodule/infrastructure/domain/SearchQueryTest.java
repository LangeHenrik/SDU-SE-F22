package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
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
        SearchQuery s = new SearchQuery();

        HashMap<Integer, Long[]> hLong = new HashMap<>();
        hLong.put(1, new Long[]{3L, 8L});
        hLong.put(2, new Long[]{7L, 52L});
        s.addRangeLong(1, 3, 8);
        s.addRangeLong(2, 7, 52);

        HashMap<Integer, Double[]> hDouble = new HashMap<>();
        hDouble.put(1, new Double[]{8.3, 14.9});
        hDouble.put(2, new Double[]{2.7, 73.2});
        s.addRangeDouble(1, 8.3, 14.9);
        s.addRangeDouble(2, 2.7, 73.2);

        HashMap<Integer, Instant[]> hInstant = new HashMap<>();
        hInstant.put(1, new Instant[]{Instant.ofEpochSecond(1), Instant.ofEpochSecond(7)});
        hInstant.put(2, new Instant[]{Instant.ofEpochSecond(2), Instant.ofEpochSecond(16)});
        s.addRangeInstant(1, Instant.ofEpochSecond(1), Instant.ofEpochSecond(7));
        s.addRangeInstant(2, Instant.ofEpochSecond(2), Instant.ofEpochSecond(16));

        Iterator<Map.Entry<Integer, Long[]>> iteratorHLong = hLong.entrySet().iterator();
        Iterator<Map.Entry<Integer, Double[]>> iteratorHDouble = hDouble.entrySet().iterator();
        Iterator<Map.Entry<Integer, Instant[]>> iteratorHInstant = hInstant.entrySet().iterator();


        s.getRangeDouble().forEach((Integer id, Double[] boundaries) -> {
            Map.Entry<Integer, Double[]> nextDouble = iteratorHDouble.next();
           if (!nextDouble.getKey().equals(id) &&
                !Arrays.equals(nextDouble.getValue(), boundaries)) {
               fail("addRangeTest Double failure.");
           }
        });

        s.getRangeLong().forEach((Integer id, Long[] boundaries) -> {
            Map.Entry<Integer, Long[]> nextLong = iteratorHLong.next();
            if (!nextLong.getKey().equals(id) &&
                    !Arrays.equals(nextLong.getValue(), boundaries)) {
                fail("addRangeTest Long failure.");
            }
        });

        s.getRangeInstant().forEach((Integer id, Instant[] boundaries) -> {
            Map.Entry<Integer, Instant[]> nextInstant = iteratorHInstant.next();
            if (!nextInstant.getKey().equals(id) &&
                    !Arrays.equals(nextInstant.getValue(), boundaries)) {
                fail("addRangeTest Instant failure.");
            }
        });
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