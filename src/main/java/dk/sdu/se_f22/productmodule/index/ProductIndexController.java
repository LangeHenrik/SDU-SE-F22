package dk.sdu.se_f22.productmodule.index;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ProductIndexController {

    @FXML
    private void onSearchButton1(){
        String searchString = searchField1.getText();
        homepage.setVisible(false);
        resultPage.setVisible(true);
        searchResults.getItems().add("*Result for: " + searchString + "*");
    }

    @FXML
    private void onSearchButton2(){
        String searchString = searchField2.getText();
        searchResults.getItems().add("*Result for: " + searchString + "*");
    }

    @FXML
    private void onFeelingLucky(){
        homepage.setVisible(false);
        luckyPage.setVisible(true);
    }

    @FXML
    private AnchorPane homepage;

    @FXML
    private AnchorPane resultPage;

    @FXML
    private AnchorPane luckyPage;

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
    private ListView searchResults;

}
