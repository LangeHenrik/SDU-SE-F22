package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseOperator extends Filterable{
    public boolean addMisspelling(); //console
    public boolean addMisspelling(String wrong, String correct); //parameter input
    public boolean deleteMisspelling(); //console
    public boolean deleteMisspelling(String missspelling); //parameter input
    public boolean updateMisspelling(); //console
    public boolean updateMisspelling(String oldMisspelling, String newMisspelling); //parameter input
}

//Temporary
interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}
