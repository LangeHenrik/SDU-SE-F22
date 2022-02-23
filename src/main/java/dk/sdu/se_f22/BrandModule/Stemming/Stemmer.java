package dk.sdu.se_f22.BrandModule.Stemming;

import java.util.ArrayList;
import java.util.Locale;

public class Stemmer implements IStemmer {

   public String[] stem(String[] words) {
       ArrayList<String> stemmedList = new ArrayList<>();
       for (String word : words) {
           stem(word);
       }
       return stemmedList.toArray(new String[0]);
   }

   public String stem(String word) {
        return "";
   }

    private Word step1a(Word word) {
        if (word.endsWith("sses")) return word.subWord(0, word.length() - 2);
        if (word.endsWith("ies")) return word.substring(0, word.getWordString().length() - 2);
        if (word.endsWith("ss")) return word;
        if (word.endsWith("s")) return word.substring(0, word.getWordString().length() - 1);
        return word;
    }

    private Word step1b(Word word) {
        if (word.getMeasure() > 1 && word.endsWith("eed")) {
            return word.substring(0, word.getWordString().length() - 1);
        }
        if (containsVowel(word.getWordString().substring(0, word.getWordString().length() - 2)) && word.endsWith("ed") && !word.endsWith("eed")) {
            return finish1b(word.getWordString().substring(0, word.getWordString().length() - 2));
        }
        if(containsVowel(word.getWordString().substring(0, word.getWordString().length() - 3)) && word.endsWith("ing")){
            return finish1b(word.getWordString().substring(0, word.getWordString().length() - 3));
        }
        return word;
    }

    private Word finish1b(Word word) {
        switch (word.getWordString().substring(word.getWordString().length() - 2)) {
            case "at", "bl", "iz": { word.append("e"); return word; }
        }
        if (endsWithDoubleCons(word)) {
            if (!(word.endsWith("l") || word.endsWith("s") || word.endsWith("z"))) {
                return word.getWordString().substring(0, word.getWordString().length() - 1);
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
        if (containsVowel(stem) && word.endsWith("y")) {
            return stem + "i";
        }
        return word;
    }

    private Word step2(Word word) {
        word.replaceIfEnds( "rd", "d");

    }
    // Tænker dette vil give measure fra et givet ord
    // Doesn't take into account the ending vowel.



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
            if (word.isCons(word.getWordString().length() - 1 ) && word.isCons(word.getWordString().length() - 2)) return true;
        }
        return false;
    }

    private boolean containsVowel(Word word) {
        for (int i = 0; i < word.getWordString().length(); i++) {
            if (word.isVowel(i)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Stemmer stemmer = new Stemmer();
        System.out.println(stemmer.step1c(new Word("happy")));
    }
}
