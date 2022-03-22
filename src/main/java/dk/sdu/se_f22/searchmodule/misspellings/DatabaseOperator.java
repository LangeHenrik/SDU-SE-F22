package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseOperator extends Filterable{
    public void addMisspelling(); //console
    public void addMisspelling(String wrong, String correct); //parameter input
    public void deleteMisspelling(); //console
    public void deleteMisspelling(String missspelling); //parameter input
    public void updateMisspelling(); //console
    public void updateMisspelling(String correction, String oldMisspelling); //parameter input
}

//Temporary
interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}
