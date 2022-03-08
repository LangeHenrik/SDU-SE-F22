package dk.sdu.se_f22.BrandModule.Stemming;

import java.util.List;

public class Stemmer implements IStemmer {
    public String[] stem(List<String> words) {
       throw new UnsupportedOperationException("Not yet implemented");
    }

    public String stem(String word) {
       throw new UnsupportedOperationException("Not yet implemented");
   }

    private Word step1a(Word word) {
        if (word.endsWith("sses")) return word.subWord(0, word.length() - 2);
        if (word.endsWith("ies")) return word.subWord(0, word.length() - 2);
        if (word.endsWith("ss")) return word;
        if (word.endsWith("s")) return word.subWord(0, word.length() - 1);
        return word;
    }

    private Word step1b(Word word) {
        if (word.getMeasure() > 1 && word.endsWith("eed")) {
            return word.subWord(0, word.getWordString().length() - 1);
        }
        if (word.containsVowel(0, word.length() - 2) && word.endsWith("ed") && !word.endsWith("eed")) {
            return finish1b(word.subWord(0, word.getWordString().length() - 2));
        }
        if(word.containsVowel(0, word.getWordString().length() - 3) && word.endsWith("ing")){
            return finish1b(word.subWord(0, word.getWordString().length() - 3));
        }
        return word;
    }

    private Word finish1b(Word word) {
        switch (word.getWordString().substring(word.getWordString().length() - 2)) {
            case "at", "bl", "iz": { word.append("e"); return word; }
        }
        if (endsWithDoubleCons(word)) {
            if (!(word.endsWith("l") || word.endsWith("s") || word.endsWith("z"))) {
                return word.subWord(0, word.getWordString().length() - 1);
            }
        }
        if (word.getMeasure() == 1 && endsWithCVC(word)) {
            word.append("e");
            return word;
        }
        return word;
    }

    private Word step1c(Word word) {
        String stem = word.getWordString().substring(0, word.getWordString().length() - 1);
        if (word.containsVowel(0, word.length() - 1) && word.endsWith("y")) {
            return word.subWord(0, word.length() - 1);
        }
        return word;
    }

    private Word step2(Word word) {
        return word.replaceIfEnds( "rd", "d");

    }


//    private boolean endsWith(String word, char character) {
//        return word.endsWith(String.valueOf(character));
//    }

    // *o - the stem ends cvc, where the second c is not W, X or Y (e.g. -WIL, -HOP).
    private boolean endsWithCVC(Word word) {

        if (word.isCons(word.length() - 1) && word.isVowel(word.getWordString().length() - 2) && word.isCons(word.getWordString().length() - 3)) {
            switch (word.getWordString().charAt(word.getWordString().length() - 1)) {
                case 'w', 'x', 'y': return false;
                default: return true;
            }
        }

        return false;
    }

    private boolean endsWithDoubleCons(Word word) {
        if (word.getWordString().charAt(word.getWordString().length() - 1) == word.getWordString().charAt(word.getWordString().length() - 2)) {
            return word.isCons(word.getWordString().length() - 1) && word.isCons(word.getWordString().length() - 2);
        }
        return false;
    }

    public static void main(String[] args) {
        Stemmer stemmer = new Stemmer();
        System.out.println(stemmer.step1c(new Word("happy")));
    }
}
