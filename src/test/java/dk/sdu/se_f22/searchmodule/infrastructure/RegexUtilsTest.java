package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.util.RegexUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilsTest {

    List<String> stringList;

    @BeforeEach
    void setUp() {
        stringList = new ArrayList<>();
        stringList.add(" ");
        stringList.add("|");
        stringList.add("p{Punct}");
    }

    @Test
    void convertDelimitersToRegex() {
        assertEquals("\\ |\\||\\p{Punct}", RegexUtils.convertStringListToRegexString(stringList));
    }
}