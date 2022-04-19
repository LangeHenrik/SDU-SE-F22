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

    private SearchModuleImpl searchModule = new SearchModuleImpl();

    @FXML
    public void initialize() {
        searchModule = new SearchModuleImpl();
    }

    @FXML
    public void onSem1SearchButton() {
        try {
            SearchHits searchResults = searchModule.search(SEM1customerSearchBar.getText());
            if (!searchResults.getContents().isEmpty()) {
                for (var content : searchResults.getContents()) {
                    SEM1customerText.appendText(content.toString() + "\n");
                }
            }
            if (!searchResults.getContents().isEmpty()) {
                for (var brand : searchResults.getBrands()) {
                    SEM1customerText.appendText(brand.toString() + "\n");
                }
            }
            if (!searchResults.getContents().isEmpty()) {
                for (var product : searchResults.getProducts()) {
                    SEM1customerText.appendText(product.toString() + "\n");
                }
            }
            brandHitsCounter.setText(String.valueOf(searchResults.getBrands().size()));
            productHitsCounter.setText(String.valueOf(searchResults.getProducts().size()));

        } catch (NoSuchElementException e) {
            System.out.println("No elements found");
            SEM1customerText.appendText("No elements found");
        }
    }

    public SearchModuleImpl getSearchModule() {
        return searchModule;
    }
}
