package dk.sdu.se_f22.brandmodule.stemming;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StemmingUtilitiesTest {

    @Test
    @DisplayName("Step 1a Tests")
    public void testStep1a() {
        assertEquals("caress", StemmingUtilities.step1a(new Word("caresses")).getWordString());
        assertEquals("poni", StemmingUtilities.step1a(new Word("ponies")).getWordString());
        assertEquals("ti", StemmingUtilities.step1a(new Word("ties")).getWordString());
        assertEquals("caress", StemmingUtilities.step1a(new Word("caress")).getWordString());
        assertEquals("cat", StemmingUtilities.step1a(new Word("cats")).getWordString());
    }

    @Test
    @DisplayName("Step 1b Tests")
    public void testStep1b() {
        assertEquals("feed", StemmingUtilities.step1b(new Word("feed")).getWordString());
        assertEquals("agree", StemmingUtilities.step1b(new Word("agreed")).getWordString());
        assertEquals("plaster", StemmingUtilities.step1b(new Word("plastered")).getWordString());
        assertEquals("bled", StemmingUtilities.step1b(new Word("bled")).getWordString());
        assertEquals("motor", StemmingUtilities.step1b(new Word("motoring")).getWordString());
        assertEquals("sing", StemmingUtilities.step1b(new Word("sing")).getWordString());
    }

    @Test
    @DisplayName("Finish 1b Tests")
    public void testFinish1b() {
        assertEquals("conflate", StemmingUtilities.finish1b(new Word("conflat")).getWordString());
        assertEquals("trouble", StemmingUtilities.finish1b(new Word("troubl")).getWordString());
        assertEquals("size", StemmingUtilities.finish1b(new Word("siz")).getWordString());
        assertEquals("hop", StemmingUtilities.finish1b(new Word("hopp")).getWordString());
        assertEquals("tan", StemmingUtilities.finish1b(new Word("tann")).getWordString());
        assertEquals("fall", StemmingUtilities.finish1b(new Word("fall")).getWordString());
        assertEquals("hiss", StemmingUtilities.finish1b(new Word("hiss")).getWordString());
        assertEquals("fizz", StemmingUtilities.finish1b(new Word("fizz")).getWordString());
        assertEquals("fail", StemmingUtilities.finish1b(new Word("fail")).getWordString());
        assertEquals("file", StemmingUtilities.finish1b(new Word("fil")).getWordString());
    }

    @Test
    @DisplayName("Step 1c Tests")
    public void testStep1c() {
        assertEquals("happi", StemmingUtilities.step1c(new Word("happy")).getWordString());
        assertEquals("sky", StemmingUtilities.step1c(new Word("sky")).getWordString());
    }

    @Test
    @DisplayName("Step 2 Tests")
    public void testStep2(){
        assertEquals("relate", StemmingUtilities.step2(new Word("relational")).getWordString());
        assertEquals("condition", StemmingUtilities.step2(new Word("conditional")).getWordString());
        assertEquals("rational", StemmingUtilities.step2(new Word("rational")).getWordString());
        assertEquals("valence", StemmingUtilities.step2(new Word("valenci")).getWordString());
        assertEquals("hesitance", StemmingUtilities.step2(new Word("hesitanci")).getWordString());
        assertEquals("digitize", StemmingUtilities.step2(new Word("digitizer")).getWordString());
        assertEquals("conformable", StemmingUtilities.step2(new Word("conformabli")).getWordString());
        assertEquals("radical", StemmingUtilities.step2(new Word("radicalli")).getWordString());
        assertEquals("different", StemmingUtilities.step2(new Word("differentli")).getWordString());
        assertEquals("vile", StemmingUtilities.step2(new Word("vileli")).getWordString());
        assertEquals("analogous", StemmingUtilities.step2(new Word("analogousli")).getWordString());
        assertEquals("vietnamize", StemmingUtilities.step2(new Word("vietnamization")).getWordString());
        assertEquals("predicate", StemmingUtilities.step2(new Word("predication")).getWordString());
        assertEquals("operate", StemmingUtilities.step2(new Word("operator")).getWordString());
        assertEquals("feudal", StemmingUtilities.step2(new Word("feudalism")).getWordString());
        assertEquals("decisive", StemmingUtilities.step2(new Word("decisiveness")).getWordString());
        assertEquals("hopeful", StemmingUtilities.step2(new Word("hopefulness")).getWordString());
        assertEquals("callous", StemmingUtilities.step2(new Word("callousness")).getWordString());
        assertEquals("formal", StemmingUtilities.step2(new Word("formaliti")).getWordString());
        assertEquals("sensitive", StemmingUtilities.step2(new Word("sensitiviti")).getWordString());
        assertEquals("sensible", StemmingUtilities.step2(new Word("sensibiliti")).getWordString());
    }

    @Test
    @DisplayName("Step 3 Tests")
    public void testStep3(){
        assertEquals("triplic", StemmingUtilities.step3(new Word("triplicate")).getWordString());
        assertEquals("form", StemmingUtilities.step3(new Word("formative")).getWordString());
        assertEquals("formal", StemmingUtilities.step3(new Word("formalize")).getWordString());
        assertEquals("electric", StemmingUtilities.step3(new Word("electriciti")).getWordString());
        assertEquals("electric", StemmingUtilities.step3(new Word("electrical")).getWordString());
        assertEquals("hope", StemmingUtilities.step3(new Word("hopeful")).getWordString());
        assertEquals("good", StemmingUtilities.step3(new Word("goodness")).getWordString());
    }

    @Test
    @DisplayName("Step 4 Tests")
    public void testStep4() {
        assertEquals("reviv", StemmingUtilities.step4(new Word("revival")).getWordString());
        assertEquals("allow", StemmingUtilities.step4(new Word("allowance")).getWordString());
        assertEquals("infer", StemmingUtilities.step4(new Word("inference")).getWordString());
        assertEquals("airlin", StemmingUtilities.step4(new Word("airliner")).getWordString());
        assertEquals("gyroscop", StemmingUtilities.step4(new Word("gyroscopic")).getWordString());
        assertEquals("adjust", StemmingUtilities.step4(new Word("adjustable")).getWordString());
        assertEquals("defens", StemmingUtilities.step4(new Word("defensible")).getWordString());
        assertEquals("irrit", StemmingUtilities.step4(new Word("irritant")).getWordString());
        assertEquals("replac", StemmingUtilities.step4(new Word("replacement")).getWordString());
        assertEquals("adjust", StemmingUtilities.step4(new Word("adjustment")).getWordString());
        assertEquals("depend", StemmingUtilities.step4(new Word("dependent")).getWordString());
        assertEquals("adopt", StemmingUtilities.step4(new Word("adoption")).getWordString());
        assertEquals("homolog", StemmingUtilities.step4(new Word("homologou")).getWordString());
        assertEquals("commun", StemmingUtilities.step4(new Word("communism")).getWordString());
        assertEquals("activ", StemmingUtilities.step4(new Word("activate")).getWordString());
        assertEquals("angular", StemmingUtilities.step4(new Word("angulariti")).getWordString());
        assertEquals("homolog", StemmingUtilities.step4(new Word("homologous")).getWordString());
        assertEquals("effect", StemmingUtilities.step4(new Word("effective")).getWordString());
        assertEquals("bowdler", StemmingUtilities.step4(new Word("bowdlerize")).getWordString());
    }

    @Test
    @DisplayName("Step 5a Tests")
    public void testStep5a() {
        assertEquals("probat", StemmingUtilities.step5a(new Word("probate")).getWordString());
        assertEquals("rate", StemmingUtilities.step5a(new Word("rate")).getWordString());
        assertEquals("ceas", StemmingUtilities.step5a(new Word("cease")).getWordString());
    }

    @Test
    @DisplayName("Step 5b tests")
    public void testStep5b(){
        assertEquals("control", StemmingUtilities.step5b(new Word("controll")).getWordString());
        assertEquals("roll", StemmingUtilities.step5b(new Word("roll")).getWordString());
    }
}
