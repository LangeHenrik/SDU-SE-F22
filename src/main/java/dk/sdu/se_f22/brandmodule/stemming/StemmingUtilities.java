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
        //   >:(
         if (word.getMeasure() > 0) {
            switch (word.getWordString().charAt(word.length() - 2)) {
                case 'a':
                    word.replaceM0(word, "ational", "ate");
                    word.replaceM0(word, "tional", "tion");
                    break;
                case 'c':
                    word.replaceM0(word,"enci", "ence");
                    word.replaceM0(word,"anci", "ance");
                    break;
                case 'e':
                    word.replaceM0(word,"izer", "ize");
                    break;
                case 'l':
                    word.replaceM0(word,"abli", "able");
                    word.replaceM0(word,"alli", "al");
                    word.replaceM0(word,"entli", "ent");
                    word.replaceM0(word,"eli", "e");
                    word.replaceM0(word,"ousli", "ous");
                    break;
                case 'o':
                    word.replaceM0(word,"ization", "ize");
                    word.replaceM0(word,"ation", "ate");
                    word.replaceM0(word,"ator", "ate");
                    break;
                case 's':
                    word.replaceM0(word,"alism", "al");
                    word.replaceM0(word,"iveness", "ive");
                    word.replaceM0(word,"fulness", "ful");
                    word.replaceM0(word,"ousness", "ous");
                    break;
                case 't':
                    word.replaceM0(word,"aliti","al");
                    word.replaceM0(word,"iviti", "ive");
                    word.replaceM0(word,"biliti", "ble");
            }
        }
        return word;
    }

    public static Word step3(Word word){
        if (word.getMeasure() > 0) {
            word.replaceM0(word,"icate", "ic");
            word.replaceM0(word,"ative", "");
            word.replaceM0(word,"alize", "al");
            word.replaceM0(word,"iciti", "ic");
            word.replaceM0(word,"ical", "ic");
            word.replaceM0(word,"ful", "");
            word.replaceM0(word,"ness", "");
        }
        return word;
    }

    public static Word step4 (Word word) {
            switch (word.getWordString().charAt(word.length() - 2)) {
                case 'a':
                    word.replaceM1(word,"al", "");
                    break;
                case 'c':
                    word.replaceM1(word,"ance", "");
                    word.replaceM1(word,"ence", "");
                    break;
                case 'e':
                    word.replaceM1(word,"er", "");
                    break;
                case 'i':
                    word.replaceM1(word,"ic", "");
                    break;
                case 'l':
                    word.replaceM1(word,"able", "");
                    word.replaceM1(word,"ible", "");
                    break;
                case 'n':
                    word.replaceM0(word,"ant", "");
                    word.replaceM0(word,"ement", "");
                    word.replaceM1(word,"ment", "");
                    word.replaceM1(word,"ent", "");
                    break;
                case 'o':
                    word.replaceM1(word,"ou", "");
                    break;
                case 's':
                    word.replaceM1(word,"ism", "");
                    break;
                case 't':
                    word.replaceM1(word, "ate", "");
                    word.replaceM1(word, "iti", "");
                    break;
                case 'u':
                    word.replaceM1(word, "ous", "");
                case 'v':
                    word.replaceM1(word,"ive", "");
                    break;
                case 'z':
                    word.replaceM1(word,"ize", "");
                    break;
                }
            if (word.endsWith("sion") || word.endsWith("tion")) {
                word.replaceM1(word,"ion", "");
            }

        return word;
    }

    public static Word step5a (Word word) {
        if (word.endsWith("e")) {
            word.replaceM1(word, "e", "");
        }



        if (word.getMeasure() == 1 && !word.endsWithCVC()){
            return word.replaceIfEnds("e", "");
        }
        return word;
    }

    public static Word step5b (Word word) {
        if (word.endsWithDoubleCons() && word.endsWith("l")) {
            word.replaceM1(word, word.subWord(word.length() - 1, word.length()).getWordString(), "");
        }
        return word;
    }
}
