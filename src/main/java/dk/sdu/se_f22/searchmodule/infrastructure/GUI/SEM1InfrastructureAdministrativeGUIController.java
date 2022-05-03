package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.DelimiterSettings;
import dk.sdu.se_f22.searchmodule.infrastructure.IllegalChars;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SEM1InfrastructureAdministrativeGUIController implements Initializable {

    @FXML
    private RadioButton delimiterRB;
    @FXML
    private RadioButton forbiddenCharsRB;
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Label delimitersCount;
    @FXML
    private Label forbiddenCharsCount;
    @FXML
    private TextField addTextField;
    @FXML
    private TextField removeTextField;
    @FXML
    private TextArea delimitersTextArea;
    @FXML
    private TextArea forbiddenCharsTextArea;

    private DelimiterSettings delimiterSettings;
    private IllegalChars illegalChars;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delimiterSettings = new DelimiterSettings();
        illegalChars = new IllegalChars();
        showDelimiters();
        showForbiddenChars();
    }

    public void addBtn(ActionEvent e) {
        if (delimiterRB.isSelected()) {
            addDelimiter();
        }
        else if (forbiddenCharsRB.isSelected()) {
            addForbiddenChar();
        }
    }

    public void removeBtn(ActionEvent e) {
        if (delimiterRB.isSelected()) {
            removeDelimiter();
        }
        else if (forbiddenCharsRB.isSelected()) {
            removeForbiddenChar();
        }
    }

    private void addDelimiter() {
        delimiterSettings.addDelimiter(addTextField.getText());
        addTextField.clear();
        showDelimiters();
    }

    private void removeDelimiter() {
        delimiterSettings.removeDelimiter(removeTextField.getText());
        removeTextField.clear();
        showDelimiters();
    }

    private void addForbiddenChar() {
        illegalChars.addChar(addTextField.getText());
        addTextField.clear();
        showForbiddenChars();
    }

    private void removeForbiddenChar() {
        illegalChars.removeChar(removeTextField.getText());
        removeTextField.clear();
        showForbiddenChars();
    }

    private void showDelimiters() {
        delimitersTextArea.clear();
        for (String s : delimiterSettings.getDelimiters()) {
            delimitersTextArea.appendText("\"" + s + "\"" + " ,");
        }
        delimitersCount.setText("Delimiters: " + delimiterSettings.getDelimiters().size());
    }

    private void showForbiddenChars() {
        forbiddenCharsTextArea.clear();
        for (String s : illegalChars.illegalCharsFromDB()) {
            forbiddenCharsTextArea.appendText("\"" + s + "\"" + " ,");
        }
        forbiddenCharsCount.setText("Forbidden Characters: " + illegalChars.illegalCharsFromDB().size());
    }
}
