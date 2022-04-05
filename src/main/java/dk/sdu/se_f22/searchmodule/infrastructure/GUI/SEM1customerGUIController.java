package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SEM1customerGUIController {

    @FXML
    private Button SEM1customerSearchbtn;
    @FXML
    private TextField SEM1customerSearchBar;
    @FXML
    private TextArea SEM1customerText;
    private SearchModuleImpl searchModule;


    @FXML
    public void onSem1SearchButton(){
        SEM1customerText.setText(searchModule.search(SEM1customerSearchBar.getText()).toString());
    }
}
