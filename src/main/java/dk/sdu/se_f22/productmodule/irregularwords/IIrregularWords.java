package dk.sdu.se_f22.productmodule.irregularwords;
import java.util.ArrayList;
import java.util.List;
public interface IIrregularWords {

        boolean createIRWord(int ID, String Word);

        boolean createIRWord(String tableWord, String insertionWord);

        boolean deleteIRWord(String theWord);

        boolean updateIRWord(String originalWord, String updatedWord);

        Boolean readIRWord();

        ArrayList<String> getIRWord(String word);

        int getIndex(String word);

        boolean createBackup();

        boolean loadBackup();

        List<String> searchForIrregularWords(List<String> arrayList);
}
