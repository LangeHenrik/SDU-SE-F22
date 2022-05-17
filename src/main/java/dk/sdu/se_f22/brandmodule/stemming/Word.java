package dk.sdu.se_f22.brandmodule.stemming;

public class Word {
    String word;

    public Word(String word) {
        this.word = word.toLowerCase();
    }

    public int getMeasure() {
        char[] characters = word.toLowerCase().toCharArray();
        int mCounter = 0;
        for (int i = 0; i < characters.length; i++) {
            if (i > 0) {
                if (isCons(i) && isVowel(i-1)){
                    mCounter++;
                }
            }
        }
        return mCounter;
    }

    public Word getBase(String end) {
        Word base = this.subWord(0, this.length() - end.length());
        return base;
    }

    /**
     *
     * @param idx
     * @return
     */
    public boolean isVowel(int idx) {
        // If the character isn't a consonant, it's a vowel.
        return !isCons(idx);
    }

    public boolean isCons(int idx) {
        char character = word.toCharArray()[idx];
        switch (character) {
            // If the character is a normal vowel, it obviously isn't a consonant.
            case 'a', 'e', 'i', 'o', 'u', 'æ', 'ø', 'å': return false;
            // If the character is a y, we need to check for the character's index minus one and return the reverse.
            case 'y': {
                if (idx == 0) return true;
                return !isCons(idx - 1);
            }

            default: return true;
        }
    }

    public Word replaceIfEnds(String match, String replace) {
        if (word.endsWith(match)) {
            word = word.replace(match, replace);
        }
        return this;
    }

    public Word replaceM0(Word word, String end, String replace){
        if (endsWith(end)) {
            Word base = word.subWord(0, word.length() - end.length());
            if (base.getMeasure() > 0){
                this.word = base.append(replace).getWordString();
            }
        }
        return word;
    }

    public Word replaceM1(Word word, String end, String replace){
        if (endsWith(end)) {
            Word base = word.subWord(0, word.length() - end.length());
            if (base.getMeasure() > 1){
                this.word = base.append(replace).getWordString();
            }
        }
        return word;
    }


    public boolean endsWith(String string) {
        return word.endsWith(string);
    }

    public String getWordString() {
        return this.word;
    }

    public Word append(String appendString) {
        word = word + appendString;
        return this;
    }

    public int length() {
        return word.length();
    }

    public Word subWord(int beginIndex, int endIndex) {
        return new Word(this.getWordString().substring(beginIndex, endIndex));
    }

    public boolean containsVowel(int startIndex, int endIndex) {
        if (endIndex < 0) return false;
        if (startIndex > endIndex) return false;
        String stem = getWordString().substring(startIndex, endIndex);
        for (int i = 0; i < stem.length(); i++) {
            if (this.isVowel(i)) return true;
        }
        return false;
    }

    public boolean endsWithDoubleCons() {
        if (this.getWordString().charAt(this.getWordString().length() - 1) == this.getWordString().charAt(this.getWordString().length() - 2)) {
            return isCons(this.getWordString().length() - 1) && isCons(this.getWordString().length() - 2);
        }
        return false;
    }

    // *o - the stem ends cvc, where the second c is not W, X or Y (e.g. -WIL, -HOP).
    public boolean endsWithCVC() {
        if (this.getWordString().length() <= 2) return false;

        if (isCons(word.length() - 1) && isVowel(this.getWordString().length() - 2) && isCons(this.getWordString().length() - 3)) {
            switch (this.getWordString().charAt(this.getWordString().length() - 1)) {
                case 'w', 'x', 'y': return false;
                default: return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return this.word;
    }
}
