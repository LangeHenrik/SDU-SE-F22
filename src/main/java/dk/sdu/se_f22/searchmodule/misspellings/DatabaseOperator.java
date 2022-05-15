package dk.sdu.se_f22.searchmodule.misspellings;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseOperator extends Filterable {
    public boolean addMisspelling(String wrong, String correct); //parameter input
    public boolean deleteMisspelling(String missspelling); //parameter input
    public boolean updateMisspelling(String oldMisspelling, String newMisspelling); //parameter input
}

