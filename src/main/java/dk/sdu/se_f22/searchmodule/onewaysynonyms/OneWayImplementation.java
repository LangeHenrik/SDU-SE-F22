package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OneWayImplementation implements OneWayInterface {
    DatabaseAPI db = new DatabaseAPI();

    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        return null;
    }

    @Override
    public void createItem() {
        db.addItem("Cake");
    }

    @Override
    public void changeItemPlacement() {
        db.updateSuperId("Cake", 2);
    }

    @Override
    public void showCatalog() {
        ResultSet content = db.read();
        try {
            while (content.next()) {
                System.out.println(content.getInt(1) + "-" + content.getString(2) + "-" + content.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeItemName() {
        db.updateName(1, "Football");
    }

    @Override
    public void returnNameList() {

    }
}
