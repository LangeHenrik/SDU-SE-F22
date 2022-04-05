package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchLog;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.NoSuchElementException;

public class SEM1customerGUIController {

    @FXML
    private TextField SEM1customerSearchBar;
    @FXML
    private TextArea SEM1customerText;
    @FXML
    private Label brandHitsCounter;
    @FXML
    private Label productHitsCounter;
    private SearchLog searchLog;
    private SearchModuleImpl searchModule = new SearchModuleImpl();


    @FXML
    public void onSem1SearchButton(){
        try {
            SearchHits searchResults = searchModule.search(SEM1customerSearchBar.getText());
            for (var content : searchResults.getContents()) {
                SEM1customerText.appendText(content.toString()+"\n");
            }
            for (var brand : searchResults.getBrands()) {
                SEM1customerText.appendText(brand.toString()+"\n");
            }
            for (var product : searchResults.getProducts()) {
                SEM1customerText.appendText(product.toString() + "\n");
            }
        } catch (NoSuchElementException e){
            System.out.println("No elements found");
            SEM1customerText.appendText("No elements found");
        }
        brandHitsCounter.setText(String.valueOf(searchLog.getBrandsCounter()));
        productHitsCounter.setText(String.valueOf(searchLog.getProductCounter()));

    }
}
