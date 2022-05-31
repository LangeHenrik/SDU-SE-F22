package dk.sdu.se_f22.contentmodule.management.UI;

import dk.sdu.se_f22.contentmodule.management.Domain.Indexing;
import dk.sdu.se_f22.contentmodule.management.Domain.Management;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelloController implements Initializable {

    @FXML
    private TableView<Edit> contentTable;

    @FXML
    private TableColumn<Edit, String> content_table_article_number;

    @FXML
    private TableColumn<Edit, String> content_table_html;

    @FXML
    private TableColumn<Edit, Integer> content_table_id;

    @FXML
    private TableColumn<Edit, String> content_table_time;

    @FXML
    private Button edit_file_button, interval_button, open_file_button, publish_button;

    @FXML
    private HTMLEditor editor;

    @FXML
    private Spinner<Integer> interval_counter;

    @FXML
    private TextArea pagePath;

    private File htmlFile;
    private String htmlText = "";

    private SpinnerValueFactory<Integer> valueFactory;
    private int interval;

    private ObservableList<Edit> list = FXCollections.observableArrayList();

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

    private void saveEdits() {
        htmlText = editor.getHtmlText();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(htmlText);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendToDatabase(htmlText);
        updateTable(htmlText);
    }
    private void sendToDatabase(String htmlText){
        Management.Create(11223344,htmlText,0);
    }

    private void updateTable(String Html) {
        list.add(new Edit(1, Html, "Timestamp", "article"));
        contentTable.setItems(list);
    }

    private void insertPrevData(){
        //
    }

    private void indexInterval() {
        System.out.println(interval);
        Management.updateIndexInterval(interval);
    }

    private void editFile() {
        try {
            Scanner s = new Scanner(htmlFile);
            while (s.hasNext())
                htmlText += s.nextLine();
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
        content_table_id.setCellValueFactory(new PropertyValueFactory<Edit, Integer>("id"));
        content_table_html.setCellValueFactory(new PropertyValueFactory<Edit, String>("html"));
        content_table_time.setCellValueFactory(new PropertyValueFactory<Edit, String>("timestamp"));
        content_table_article_number.setCellValueFactory(new PropertyValueFactory<Edit, String>("articleNr"));

        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1440);
        valueFactory.setValue((int) Indexing.getInterval());
        interval_counter.setValueFactory(valueFactory);
        interval = interval_counter.getValue() * 60 * 1000;
    }
}

