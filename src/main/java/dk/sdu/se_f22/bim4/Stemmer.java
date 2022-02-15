package dk.sdu.se_f22.bim4;

public class Stemmer {

    /*
    private void getVowelIndexesOld(String word) {
        char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};
        for(char character : word.toLowerCase().toCharArray()) {
            for (int i = 0; i < vowels.length; i++) {
                if (character == vowels[i]) {
                    if (vowels[i] == 'y') {
                        if (vowels[i-1])
                    }
                    System.out.println("Test");
                }
            }
        }
    }*/

    private boolean isVowel(String word, int idx) {
        // If the character isn't a consonant, it's a vowel.
        return !isCons(word, idx);
    }

    private boolean isCons(String word, int idx) {
        char character = word.toCharArray()[idx];
        switch (character) {
            // If the character is a normal vowel, it obviously isn't a consonant.
            case 'a', 'e', 'i', 'o', 'u': return false;
            // If the character is a y, we need to check for the character's index minus one and return the reverse.
            case 'y': return !isCons(word, idx - 1);
            default: return true;
        }
    }
}
