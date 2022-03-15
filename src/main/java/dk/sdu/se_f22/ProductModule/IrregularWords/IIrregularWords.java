package dk.sdu.se_f22.ProductModule.IrregularWords;

import java.util.ArrayList;

public interface IIrregularWords {

        public void createIRWord(int ID, String Word);

        public void deleteIRWord(int serialKey);

        public void updateIRWord();

        public void readIRWord();

        public void getIRWord();

        public void createColumnIR(String CName);

        public void deleteColumnIR(String CName);

        public void createBackup();

        public void loadBackup();

        public ArrayList<String> searchForIrregularWords(ArrayList<String> arrayList);
    }
