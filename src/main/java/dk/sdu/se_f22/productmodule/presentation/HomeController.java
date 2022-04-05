package dk.sdu.se_f22.productmodule.presentation;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.productmodule.infrastructure.data.TokenParameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomeController {
    public static File file;
    
    @FXML
    private TextField deliInput;

    @FXML
    private Label deliLabel;

    @FXML
    private TextField ignoredInput;

    @FXML
    private Label ignoredLabel;

    @FXML
    private Button indexProductsBtn;

    @FXML
    private AnchorPane main;

    @FXML
    private Button saveBtn;

    @FXML
    private AnchorPane tokenParameter;

    @FXML
    void initialize() {
        deliInput.setText(ProductIndexInfrastructure.getInstance().getTokenParameter().getDelimiter());
        ignoredInput.setText(String.join(", ", ProductIndexInfrastructure.getInstance().getTokenParameter().getIgnoredChars()));
    }

    public void handleSaveButton(ActionEvent actionEvent) {
        List<String> ignoredChars = Arrays.asList(ignoredInput.getText().split(","));
        TokenParameter tokenParameter = new TokenParameter(deliInput.getText(),ignoredChars);
        ProductIndexInfrastructure.getInstance().setTokenParameter(tokenParameter);
    }

    @FXML
    public void handleChooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(main.getScene().getWindow());
        //App.productManager = new ProductManager(file.getAbsolutePath());

        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("productlist.fxml"));
            Scene scene = new Scene(fxmlLoader, 800,600);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
