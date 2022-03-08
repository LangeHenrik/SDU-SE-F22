package dk.sdu.se_f22.brandmodule.stemming;

public class Word {
    String word;

    public Word(String word) {
        this.word = word.toLowerCase();
    }

    // Get the measure of the word
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
            case 'y': return !isCons(idx - 1);
            default: return true;
        }
    }

    public Word replaceIfEnds(String match, String replace) {
        if (word.endsWith(match)) {
            word.replace(match, replace);
        }
        return this;
    }

    public boolean endsWith(String string) {
        return word.endsWith(string);
    }

    public String getWordString() {
        return this.word;
    }

    public void append(String appendString) {
        word = word + appendString;
    }

    public int length() {
        return word.length();
    }
    public Word subWord(int beginIndex, int endIndex) {
        return new Word(this.getWordString().substring(beginIndex, endIndex));
    }

    public boolean containsVowel(int startIndex, int endIndex) {
        for (int i = 0; i < this.getWordString().length(); i++) {
            if (this.isVowel(i)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.word;
    }
}
