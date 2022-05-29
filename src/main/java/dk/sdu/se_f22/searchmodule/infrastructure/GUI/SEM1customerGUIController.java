package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SEM1customerGUIController {

    @FXML
    private TextField SEM1customerSearchBar;
    @FXML
    private TextArea SEM1customerText;

    @FXML
    private Label brandHitsCounter;
    @FXML
    private Label productHitsCounter;

    private SearchModuleImpl searchModule;

    @FXML
    public void initialize() {
    }

    @FXML
    public void onSem1SearchButton() {
        if (searchModule == null) {
            SEM1customerText.appendText("No searchmodule set\n");
            return;
        }

        boolean foundResult = false;
        SearchHits searchResults = searchModule.search(SEM1customerSearchBar.getText());
        if (!searchResults.getContents().isEmpty()) {
            foundResult = true;
            for (var content : searchResults.getContents()) {
                SEM1customerText.appendText(content.toString() + "\n");
            }
        }
        if (!searchResults.getBrands().isEmpty()) {
            foundResult = true;
            for (var brand : searchResults.getBrands()) {
                SEM1customerText.appendText(brand.toString() + "\n");
            }
        }
        if (!searchResults.getProducts().isEmpty()) {
            foundResult = true;
            for (var product : searchResults.getProducts()) {
                SEM1customerText.appendText(product.toString() + "\n");
            }
        }
        brandHitsCounter.setText(String.valueOf(searchResults.getBrands().size()));
        productHitsCounter.setText(String.valueOf(searchResults.getProducts().size()));

        if (!foundResult) {
            System.out.println("No elements found");
            SEM1customerText.appendText("No elements found" + "\n");
        }
    }

    public SearchModuleImpl getSearchModule() {
        return searchModule;
    }

    public void setSearchModule(SearchModuleImpl searchModule) {
        this.searchModule = searchModule;
    }

    public Label getBrandHitsCounter() {
        return brandHitsCounter;
    }

    public Label getProductHitsCounter() {
        return productHitsCounter;
    }
}
