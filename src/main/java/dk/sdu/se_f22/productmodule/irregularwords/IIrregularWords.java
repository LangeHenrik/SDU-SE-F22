package dk.sdu.se_f22.productmodule.irregularwords;
import java.util.ArrayList;
import java.util.List;
public interface IIrregularWords {

        void createIRWord(int ID, String Word);

        void deleteIRWord(int serialKey);

        void updateIRWord();

        void readIRWord();

        void getIRWord();

        void createIRColumn(String Cname, String dataType, String constraints);

        void deleteColumnIR(String CName);

        void createBackup();

        void loadBackup();

        List<String> searchForIrregularWords(List<String> arrayList);
}
