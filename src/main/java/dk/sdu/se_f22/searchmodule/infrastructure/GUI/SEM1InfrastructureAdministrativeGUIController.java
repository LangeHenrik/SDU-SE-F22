package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.DelimiterSettings;
import dk.sdu.se_f22.searchmodule.infrastructure.IllegalChars;
import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.Tokenizer;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SEM1InfrastructureAdministrativeGUIController implements Initializable {

    @FXML
    private RadioButton delimiterRB;
    @FXML
    private RadioButton forbiddenCharsRB;
    @FXML
    private Label delimitersCount;
    @FXML
    private Label forbiddenCharsCount;
    @FXML
    private TextField addTextField;
    @FXML
    private TextArea delimitersTextArea;
    @FXML
    private TextArea forbiddenCharsTextArea;
    @FXML
    private TextField adminTokenTestEntry;
    @FXML
    private TextArea adminTestRemovalCharText;
    @FXML
    private Label adminRawTokensLabel;
    @FXML
    private Label adminFilteredTokensLabel;
    @FXML
    private TextArea adminRawTokensFeild;
    @FXML
    private TextArea adminFilteredTokensFeild;
    @FXML
    private Label adminSearchresultCount;
    @FXML
    private TextArea adminSearchResults;

    private DelimiterSettings delimiterSettings;
    private IllegalChars illegalChars;
    private Tokenizer tokenizer;
    private SearchModuleImpl searchModule;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delimiterSettings = new DelimiterSettings();
        illegalChars = new IllegalChars();
        tokenizer = new Tokenizer();
        searchModule = new SearchModuleImpl();
        showDelimiters();
        showForbiddenChars();
    }

    public void adminSearchTestHandler(ActionEvent e){
        String searchStringPostIllegalChars = illegalChars.removeForbiddenChars(adminTokenTestEntry.getText());
        adminTestRemovalCharText.setText(searchStringPostIllegalChars);
        List<String> unfilteredTokens = tokenizer.tokenize(searchStringPostIllegalChars);

        adminRawTokensLabel.setText(String.valueOf(unfilteredTokens.size()));
        prettyWriteToTextField(unfilteredTokens, adminRawTokensFeild, 3);

        List<String> filteredTokens = searchModule.filterTokens(unfilteredTokens);
        adminFilteredTokensLabel.setText(String.valueOf(filteredTokens.size()));
        prettyWriteToTextField(filteredTokens, adminFilteredTokensFeild, 3);

        SearchHits searchHits = searchModule.search(adminTokenTestEntry.getText());
        adminSearchResults.setText("");
        for (Product product : searchHits.getProducts()) {
            adminSearchResults.appendText(product.toString());
        }
        for (Brand brand : searchHits.getBrands()) {
            adminSearchResults.appendText(brand.toString());
        }
        for (Product product : searchHits.getProducts()) {
            adminSearchResults.appendText(product.toString());
        }

        adminSearchresultCount.setText(String.valueOf((searchHits.getProducts().size() + searchHits.getBrands().size() + searchHits.getProducts().size())));
    }

    public void addBtn(ActionEvent e) {
        if (delimiterRB.isSelected()) {
            addDelimiter();
        } else if (forbiddenCharsRB.isSelected()) {
            addForbiddenChar();
        }
    }

    public void removeBtn(ActionEvent e) {
        if (delimiterRB.isSelected()) {
            removeDelimiter();
        } else if (forbiddenCharsRB.isSelected()) {
            removeForbiddenChar();
        }
    }

    private void addDelimiter() {
        delimiterSettings.addDelimiter(addTextField.getText());
        addTextField.clear();
        showDelimiters();
    }

    private void removeDelimiter() {
        delimiterSettings.removeDelimiter(addTextField.getText());
        addTextField.clear();
        showDelimiters();
    }

    private void addForbiddenChar() {
        illegalChars.addChar(addTextField.getText());
        addTextField.clear();
        showForbiddenChars();
    }

    private void removeForbiddenChar() {
        illegalChars.removeChar(addTextField.getText());
        addTextField.clear();
        showForbiddenChars();
    }

    private void showDelimiters() {
        delimitersTextArea.clear();
        prettyWriteToTextField(delimiterSettings.getDelimiters(), delimitersTextArea, 6);
        delimitersCount.setText("Delimiters: " + delimiterSettings.getDelimiters().size());
    }

    private void showForbiddenChars() {
        forbiddenCharsTextArea.clear();
        prettyWriteToTextField(illegalChars.illegalCharsFromDB(), forbiddenCharsTextArea, 6);
        forbiddenCharsCount.setText("Forbidden Characters: " + illegalChars.illegalCharsFromDB().size());
    }

    private void prettyWriteToTextField(List<String> elementList, TextArea textArea, int wordsInLine) {
        StringBuilder charString = new StringBuilder();
        textArea.setText("");
        wordsInLine -= 1;

        for (int i = 0; i < elementList.size(); i++) {
            charString.setLength(0);
            charString.append("\"")
                    .append(elementList.get(i))
                    .append("\"");

            if (i != 0 && i % wordsInLine == 0) {
                charString.append("\n");
            } else if (i != elementList.size() - 1) {
                charString.append(", ");
            }

            textArea.appendText(charString.toString());
        }
    }

    public void setSearchModule(SearchModuleImpl searchModule) {
        this.searchModule = searchModule;
    }
}
