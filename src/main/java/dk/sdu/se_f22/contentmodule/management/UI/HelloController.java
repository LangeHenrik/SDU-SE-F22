package dk.sdu.se_f22.contentmodule.management.UI;

import dk.sdu.se_f22.contentmodule.management.Domain.Indexing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelloController implements Initializable {

    @FXML
    private TableView<?> contains_table;

    @FXML
    private Button edit_file_button, interval_button, open_file_button, publish_button;

    @FXML
    private HTMLEditor editor;

    @FXML
    private Text edits_table;

    @FXML
    private TableView<?> employees_table;

    @FXML
    private Spinner<Integer> interval_counter;

    @FXML
    private Text log_table;

    @FXML
    private TableView<?> pages_table;

    @FXML
    private TextArea pagePath;

    private File htmlFile;
    private String htmlText = "";

    private SpinnerValueFactory<Integer> valueFactory;
    private int interval;

    public void ButtonActionHandler(ActionEvent actionEvent) {
        Button current_button = (Button) actionEvent.getSource();
        switch (current_button.getText()) {
            case "Publish" -> {
                saveEdits();
                break;
            }
            case "Open file" -> {
                openFile();
                break;
            }
            case "Edit file" -> {
                editFile();
                break;
            }
            case "Index interval" -> {
                indexInterval();
                break;
            }
        }
    }
    private void saveEdits(){
        htmlText = editor.getHtmlText();
        System.out.println(htmlText);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(htmlText);
            writer.newLine();
            writer.close();
        } catch (Exception e) {

        }
    }

    private void indexInterval() {
        System.out.println(interval);
        //Management.updateIndexInterval()
    }

    private void editFile() {
        try {
            Scanner s = new Scanner(htmlFile);
            while (s.hasNext()) {
                htmlText += s.nextLine();
            }
            editor.setHtmlText(htmlText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        htmlFile = fileChooser.showOpenDialog(null);
        pagePath.clear();
        pagePath.appendText(htmlFile.getPath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1440);
        valueFactory.setValue((int) Indexing.getInterval());
        interval_counter.setValueFactory(valueFactory);
        interval = interval_counter.getValue()*60*1000;
    }
}

