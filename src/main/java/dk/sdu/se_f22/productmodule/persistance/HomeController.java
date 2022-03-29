package dk.sdu.se_f22.productmodule.persistance;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.productmodule.infrastructure.data.TokenParameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

public class HomeController {
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
}
