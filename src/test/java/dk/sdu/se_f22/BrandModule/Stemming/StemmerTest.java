package dk.sdu.se_f22.BrandModule.Stemming;

import org.junit.jupiter.api.BeforeEach;

public class StemmerTest {

    private Stemmer stemmer;

    @BeforeEach
    private void initStemmer() {
        stemmer = new Stemmer();
    }
}
