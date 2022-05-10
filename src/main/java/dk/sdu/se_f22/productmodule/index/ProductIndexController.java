package dk.sdu.se_f22.productmodule.index;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProductIndexController {

    @FXML
    private void onSearchButton(){
        homepage.setVisible(false);
        resultPage.setVisible(true);
        searchResults.setText("test test test");
    }

    @FXML
    private void onFeelingLucky(){
        //to be implemented
    }

    @FXML
    private AnchorPane homepage;

    @FXML
    private AnchorPane resultPage;

    @FXML
    private TextField searchField1;

    @FXML
    private TextField searchField2;

    @FXML
    private Button searchButton1;

    @FXML
    private Button searchButton2;

    @FXML
    private Button feelingLucky;

    @FXML
    private TextArea searchResults;

}
