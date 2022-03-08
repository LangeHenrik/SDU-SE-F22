package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryTest {

    @Test
    void setCategoryTest() {
    }

    @Test
    void addCategoryTest() {
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