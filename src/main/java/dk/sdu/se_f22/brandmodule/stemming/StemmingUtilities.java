package dk.sdu.se_f22.brandmodule.stemming;

public class StemmingUtilities {
    public static Word step1a(Word word) {
        if (word.endsWith("sses")) return word.subWord(0, word.length() - 2);
        if (word.endsWith("ies")) return word.subWord(0, word.length() - 2);
        if (word.endsWith("ss")) return word;
        if (word.endsWith("s")) return word.subWord(0, word.length() - 1);
        return word;
    }

    public static Word step1b(Word word) {
        if (word.getMeasure() > 1 && word.endsWith("eed")) {
            return word.subWord(0, word.length() - 1);
        }
        if (word.containsVowel(0, word.length() - 2) && word.endsWith("ed") && !word.endsWith("eed")) {
            return finish1b(word.subWord(0, word.length() - 2));
        }
        if(word.containsVowel(0, word.length() - 3) && word.endsWith("ing")){
            return finish1b(word.subWord(0, word.length() - 3));
        }
        return word;
    }

    public static Word finish1b(Word word) {
        switch (word.getWordString().substring(word.getWordString().length() - 2)) {
            case "at", "bl", "iz": { word.append("e"); return word; }
        }
        if (word.endsWithDoubleCons()) {
            if (!(word.endsWith("l") || word.endsWith("s") || word.endsWith("z"))) {
                return word.subWord(0, word.getWordString().length() - 1);
            }
        }
        if (word.getMeasure() == 1 && word.endsWithCVC()) {
            word.append("e");
            return word;
        }
        return word;
    }

    public static Word step1c(Word word) {
        String stem = word.getWordString().substring(0, word.getWordString().length() - 1);
        if (word.containsVowel(0, word.length() - 1) && word.endsWith("y")) {
            return word.subWord(0, word.length() - 1).append("i");
        }
        return word;
    }

    public static Word step2(Word word) {
        return word.replaceIfEnds( "rd", "d");

    }


}
