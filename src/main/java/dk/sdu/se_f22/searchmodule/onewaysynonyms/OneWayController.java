package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class OneWayController {

    @FXML
    TextField insertNameAddItemTextfield, insertSuperIDAddItemTextfield;

    @FXML
    private void addItemButtonHandler(ActionEvent actionEvent) {
        /*if (insertSuperIDAddItemTextfield.getText().equals(null)) {
            DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield));
        }else {
            try{
                int superId = Integer.parseInt(insertSuperIDAddItemTextfield.getText());
                DatabaseAPI.addItem(insertNameAddItemTextfield.getText(),superId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }*/
    }
}
