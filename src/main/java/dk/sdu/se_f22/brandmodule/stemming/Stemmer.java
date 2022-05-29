package dk.sdu.se_f22.brandmodule.stemming;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stemmer implements IStemmer {
    public ArrayList<String> stem(List<String> words) {
        ArrayList<String> returnList = new ArrayList<>();
        for (String word : words) {
            returnList.add(stem(word));
        }
        return returnList;
    }

    public String stem(String word) {
        // If the exception exists in the database, ignore it and return the word
        if (exceptionExists(word)) return word;
        if (word.length() < 3) return word;
        Word stemmingWord = new Word(word);
        stemmingWord = StemmingUtilities.step1a(stemmingWord);
        stemmingWord = StemmingUtilities.step1b(stemmingWord);
        stemmingWord = StemmingUtilities.step1c(stemmingWord);
        stemmingWord = StemmingUtilities.step2(stemmingWord);
        stemmingWord = StemmingUtilities.step3(stemmingWord);
        stemmingWord = StemmingUtilities.step4(stemmingWord);
        stemmingWord = StemmingUtilities.step5a(stemmingWord);
        stemmingWord = StemmingUtilities.step5b(stemmingWord);
        return stemmingWord.getWordString();
    }

    public void addException(String exception) {
        exception = exception.toLowerCase();
        try {
            if (exceptionExists(exception)) return;
            ExceptionUtilities.createException(exception);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Collection<String>> getExceptions() {
        try {
            return List.of(ExceptionUtilities.getExceptions().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public void updateException(String exception, String newException) {
        exception = exception.toLowerCase();
        newException = newException.toLowerCase();
        try {
            ExceptionUtilities.updateException(exception, newException);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeException(String exception) {
        exception = exception.toLowerCase();
        try {
            if (exceptionExists(exception)) return;
            ExceptionUtilities.deleteException(exception);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exceptionExists(String exception) {
        exception = exception.toLowerCase();
        try {
            return !ExceptionUtilities.getException(exception).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Worst case, return false
        return false;
    }
}
