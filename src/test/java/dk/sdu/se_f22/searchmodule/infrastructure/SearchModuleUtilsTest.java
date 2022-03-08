package dk.sdu.se_f22.searchmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchModuleUtilsTest {

    List<String> stringList;
    @BeforeEach
    void setUp() {
        stringList = new ArrayList<>();
        stringList.add(" ");
        stringList.add("\\p{Punct}");
    }

    @Test
    void convertDelimitersToRegex() {
        assertEquals(" |\\p{Punct}",SearchModuleUtils.convertDelimitersToRegex(stringList));
    }
}