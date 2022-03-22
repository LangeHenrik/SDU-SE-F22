package dk.sdu.se_f22.productmodule.irregularwords;

import java.util.ArrayList;
import java.util.List;

public class IrregularWords implements IIrregularWords{

    public IrregularWords irregularWords;

    @Override
    public void createIRWord(int ID, String Word) {

    }

    @Override
    public void deleteIRWord(int serialKey) {

    }

    @Override
    public void updateIRWord() {

    }

    @Override
    public void readIRWord() {

    }

    @Override
    public void getIRWord() {

    }

    @Override
    public void createIRColumn(String Cname, String dataType, String constraints) {
        try {
            PreparedStatement PS = connection.prepareStatement("ALTER TABLE irwords ADD COLUMN ? ?");
            PS.setString(1, Cname);
            PS.setString(2, dataType);
            PS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!constraints.equals("")) {
            try {
                PreparedStatement ps = connection.prepareStatement("ALTER TABLE irwords ADD ? (?)");
                ps.setString(1, constraints);
                ps.setString(2, Cname);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteColumnIR(String CName) {

    }

    @Override
    public void createBackup() {

    }

    @Override
    public void loadBackup() {

    }

    @Override
    public List<String> searchForIrregularWords(List<String> arrayList) {
        return arrayList;
    }
}
