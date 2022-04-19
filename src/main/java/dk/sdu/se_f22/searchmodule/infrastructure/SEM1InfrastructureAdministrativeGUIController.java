package dk.sdu.se_f22.searchmodule.infrastructure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SEM1InfrastructureAdministrativeGUIController {

    @FXML
    private RadioButton delimiterRB;
    @FXML
    private RadioButton forbiddenCharsRB;
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TextField addTextField;
    @FXML
    private TextField removeTextField;
    @FXML
    private Label delimitersCount;
    @FXML
    private Label forbiddenCharsCount;
    @FXML
    private TextArea delimitersTextArea;
    @FXML
    private TextArea forbiddenCharsTextArea;

    private DelimiterSettings delimiterSettings;


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

    public void addDelimiter() {
        delimiterSettings.addDelimiter(addTextField.getText());
        addTextField.clear();
        showDelimiters();
    }

    public void removeDelimiter() {
        delimiterSettings.removeDelimiter(removeTextField.getText());
        removeTextField.clear();
        showForbiddenChars();
    }

    public void addForbiddenChar() {

    }

    public void removeForbiddenChar() {

    }

    private void showDelimiters() {
        for (String s : delimiterSettings.getDelimiters()) {
            delimitersTextArea.appendText("\"" + s + "\"" + " ,");
        }
    }

    private void showForbiddenChars() {

    }
}
