package dk.sdu.se_f22.brandmodule.stemming;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GUIController {

    private Stemmer stemmer;

    @FXML
    private TextArea inputTextArea, outputTextArea;
    @FXML
    private Button stemButton, removeButton;
    @FXML
    private TextField exceptionField;
    @FXML
    private ListView exceptionList;

    @FXML
    public void initialize() {
        stemmer = new Stemmer();
    }

    @FXML
    public void handleStemming() {
        outputTextArea.setText(stemmer.stem(inputTextArea.getText()));
    }
}
