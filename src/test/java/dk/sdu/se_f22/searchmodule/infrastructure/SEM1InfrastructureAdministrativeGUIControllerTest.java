package dk.sdu.se_f22.searchmodule.infrastructure;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.NodeQueryUtils.hasId;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class SEM1InfrastructureAdministrativeGUIControllerTest {
    Scene scene;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplicationAdministrativeGUI.class.getResource("SEM1_Infrastructure_AdministrativeGUI.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    private RadioButton getDelimiterRB(FxRobot robot) {
        Optional<RadioButton> delimitersRB = robot.lookup(hasText("Delimiters")).tryQueryAs(RadioButton.class);
        if (delimitersRB.isEmpty())
            fail("A button with text 'Delimiters' could not be found!");
        return delimitersRB.get();
    }

    private RadioButton getForbiddenCharsRB(FxRobot robot) {
        Optional<RadioButton> forbiddenCharRB = robot.lookup(hasText("Forbidden Characters")).tryQueryAs(RadioButton.class);
        if (forbiddenCharRB.isEmpty())
            fail("A button with text 'Forbidden Characters' could not be found!");
        return forbiddenCharRB.get();
    }

    private TextField getaddTextField(FxRobot robot) {
        Optional<TextField> addTextField = robot.lookup(hasId("addTextField")).tryQueryAs(TextField.class);
        if (addTextField.isEmpty())
            fail("A button with text 'addTextField' could not be found!");
        return addTextField.get();
    }

    private TextField getremoveTextField(FxRobot robot) {
        Optional<TextField> removeTextField = robot.lookup(hasId("removeTextField")).tryQueryAs(TextField.class);
        if (removeTextField.isEmpty())
            fail("A button with text 'removeTextField' could not be found!");
        return removeTextField.get();
    }

    private Button getAddButton(FxRobot robot) {
        Optional<Button> addButton = robot.lookup(hasText("Add")).tryQueryAs(Button.class);
        if (addButton.isEmpty())
            fail("A button with text 'Add' could not be found!");
        return addButton.get();
    }

    private Button getRemoveButton(FxRobot robot) {
        Optional<Button> removeButton = robot.lookup(hasText("Remove")).tryQueryAs(Button.class);
        if (removeButton.isEmpty())
            fail("A button with text 'Remove' could not be found!");
        return removeButton.get();
    }

    @Test
    void initialize() {
    }

    @Test
    void addBtnDelimiter(FxRobot robot) {
        Button addButton = getAddButton(robot);
        TextField addTextField = getaddTextField(robot);
        RadioButton delimitersRB = getDelimiterRB(robot);

        robot.clickOn(delimitersRB);
        robot.clickOn(addTextField);
        robot.write("29");
        robot.clickOn(addButton);
    }

    @Test
    void removeBtnDelimiter(FxRobot robot) {
        Button removeButton = getRemoveButton(robot);
        TextField removeTextField = getremoveTextField(robot);
        RadioButton delimitersRB = getDelimiterRB(robot);

        robot.clickOn(delimitersRB);
        robot.clickOn(removeTextField);
        robot.write("29");
        robot.clickOn(removeButton);
    }

    @Test
    void addForbiddenChar(FxRobot robot) {
        Button addButton = getAddButton(robot);
        TextField addTextField = getaddTextField(robot);
        RadioButton forbiddenCharRB = getForbiddenCharsRB(robot);

        robot.clickOn(forbiddenCharRB);
        robot.clickOn(addTextField);
        robot.write("29");
        robot.clickOn(addButton);
    }

    @Test
    void removeForbiddenChar(FxRobot robot) {
        Button removeButton = getRemoveButton(robot);
        TextField removeTextField = getremoveTextField(robot);
        RadioButton forbiddenCharRB = getForbiddenCharsRB(robot);

        robot.clickOn(forbiddenCharRB);
        robot.clickOn(removeTextField);
        robot.write("29");
        robot.clickOn(removeButton);
    }
}