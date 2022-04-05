package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.*;

import java.sql.SQLException;

public class OneWayController {



    @FXML
    public TextField insertNameAddItemTextfield, insertSuperIDAddItemTextfield;


    @FXML
    public void addItemButtonHandler(ActionEvent actionEvent) {
        if (insertSuperIDAddItemTextfield.getText()=="") {
            try {
                DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                int superId = Integer.parseInt(insertSuperIDAddItemTextfield.getText());
                DatabaseAPI.addItem(insertNameAddItemTextfield.getText(), superId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void insertItemReadItemTextField(ActionEvent actionEvent) {
        if (insertNameAddItemTextfield.getText() == null ) {
            try {
                DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                int superId = Integer.parseInt(insertNameAddItemTextfield.getText());
                DatabaseAPI.addItem(insertNameAddItemTextfield.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
