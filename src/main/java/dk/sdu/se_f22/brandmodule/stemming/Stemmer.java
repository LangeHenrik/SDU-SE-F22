package dk.sdu.se_f22.brandmodule.stemming;

import java.util.ArrayList;
import java.util.List;

public class Stemmer implements IStemmer {
    public ArrayList<String> stem(List<String> words) {
       throw new UnsupportedOperationException("Not yet implemented");
    }

    public String stem(String word) {
       throw new UnsupportedOperationException("Not yet implemented");
   }



    public static void main(String[] args) {
        System.out.println(StemmingUtilities.step1a(new Word("bled")));
        Stemmer stemmer = new Stemmer();
//        System.out.println(stemmer.step1b(new Word("bled")));
    }
}
