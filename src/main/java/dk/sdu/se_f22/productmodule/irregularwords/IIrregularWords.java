package dk.sdu.se_f22.productmodule.irregularwords;
import java.util.ArrayList;
import java.util.List;
public interface IIrregularWords {

        boolean createIRWord(int ID, String Word);

        boolean deleteIRWord(String theWord);

        boolean updateIRWord(String Word1, String Word2);

        void readIRWord();

        ArrayList<String> getIRWord(String word);

        boolean createIRColumn(String Cname, String dataType, String constraints);

        boolean deleteColumnIR(String CName);

        boolean createBackup();

        boolean loadBackup();

        List<String> searchForIrregularWords(List<String> arrayList);
}
