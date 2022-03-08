package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseOperator extends Filterable{

}

//Temporary
interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens) throws SQLException;
}
