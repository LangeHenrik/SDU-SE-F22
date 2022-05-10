package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilteredTokensTest {
    FilteredTokens ft;
    FilteredTokens f;
    FilteredTokens ftObjekt;
    ArrayList<Token> tokenTestToken;
    ArrayList<String> tokenTestString;
    ArrayList<Token> t;
    ArrayList<String> s;
    Token token;
    ArrayList<Token> tokenArrayList;
    Token test1;
    ArrayList<Token> tokenArrayListTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        tokenTestToken = new ArrayList<>();
        tokenTestToken.add(0, new Token("mouse", 8734));
        tokenTestToken.add(1, new Token("house", 8734));
        tokenTestToken.add(2, new Token("arouse", 8734));
        tokenTestToken.add(3, new Token("spouse", 8734));
        tokenTestToken.add(4, new Token("blouse", 8734));

        tokenTestString = new ArrayList<>();
        tokenTestString.add(0, new String("mouse"));
        tokenTestString.add(1, new String("house"));
        tokenTestString.add(2, new String("arouse"));
        tokenTestString.add(3, new String("spouse"));
        tokenTestString.add(4, new String("blouse"));

        ftObjekt = new FilteredTokens(tokenTestToken);
        ft = new FilteredTokens(1, "test");
        token = tokenTestToken.get(1);

    }

    @AfterEach
    void tearDown() {
        tokenTestToken.clear();
        tokenTestString.clear();

    }

    @Test
    void tokenToString() {
        String actualContent = FilteredTokens.tokenToString(tokenTestToken).get(1);
        String expectedContent = "house";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void stringToToken() {
        String testString = "Test";
        int testInt = 400;

        test1 = new Token("Test", 400);

        tokenArrayListTest = new ArrayList<>();
        tokenArrayListTest.add(test1);
        FilteredTokens.tokenToString(tokenArrayListTest);

        assertEquals(testString, tokenArrayListTest.get(0).getDocumentText());
        assertEquals(testInt, tokenArrayListTest.get(0).getOriginID());

    }

    @Test
    void filterTokens() {


    }
}
