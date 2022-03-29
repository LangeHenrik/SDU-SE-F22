package dk.sdu.se_f22.productmodule.irregularwords;
import java.util.ArrayList;
import java.util.List;
public interface IIrregularWords {

        boolean createIRWord(int ID, String Word);

        boolean deleteIRWord(String theWord);

        boolean updateIRWord(String originalWord, String updatedWord);

        void readIRWord();

        ArrayList<String> getIRWord(String word);

        boolean createBackup();

        boolean loadBackup();

        List<String> searchForIrregularWords(List<String> arrayList);
}
