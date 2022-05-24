package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.GUI.TestApplicationAdministrativeGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        for (int i = 0; i < 10; i++) {
            robot.clickOn(addTextField);
            robot.write("2" + i);
            robot.clickOn(addButton);
        }
    }

    @Test
    void removeBtnDelimiter(FxRobot robot) {
        Button removeButton = getRemoveButton(robot);
        TextField removeTextField = getaddTextField(robot);
        RadioButton delimitersRB = getDelimiterRB(robot);
        robot.clickOn(delimitersRB);
        for (int i = 0; i < 10; i++) {
            robot.clickOn(removeTextField);
            robot.write("2" + i);
            robot.clickOn(removeButton);
        }
    }

    @Test
    void addForbiddenChar(FxRobot robot) {
        Button addButton = getAddButton(robot);
        TextField addTextField = getaddTextField(robot);
        RadioButton forbiddenCharRB = getForbiddenCharsRB(robot);

        robot.clickOn(forbiddenCharRB);
        for (int i = 0; i < 10; i++) {
            robot.clickOn(addTextField);
            robot.write("2" + i);
            robot.clickOn(addButton);
        }
    }

    @Test
    void removeForbiddenChar(FxRobot robot) {
        Button removeButton = getRemoveButton(robot);
        TextField removeTextField = getaddTextField(robot);
        RadioButton forbiddenCharRB = getForbiddenCharsRB(robot);

        robot.clickOn(forbiddenCharRB);
        for (int i = 0; i < 10; i++) {
            robot.clickOn(removeTextField);
            robot.write("2" + i);
            robot.clickOn(removeButton);
        }
    }

    @Test
    void adminSearchTestHandlerTest(FxRobot robot){
        robot.clickOn((Node) robot.lookup(hasText("Test")).nth(2).query());

        Optional<TextField> searchTestTextFieldOptional = robot.lookup(hasId("adminTokenTestEntry")).tryQueryAs(TextField.class);
        if (searchTestTextFieldOptional.isEmpty()){
            fail("A TextFeild with the id 'adminTokenTestEntry' could not be found!");
        }
        TextField searchTestTextField = searchTestTextFieldOptional.get();

        robot.clickOn(searchTestTextField);
        robot.write("Yeet");

        Optional<Button> searchTestButtonOptional = robot.lookup(hasText("Test")).tryQueryAs(Button.class);
        if (searchTestButtonOptional.isEmpty()){
            fail("A button with text 'Test' could not be found!");
        }
        Button searchTestButton = searchTestButtonOptional.get();
        robot.clickOn(searchTestButton);

        robot.clickOn(searchTestTextField);
        robot.eraseText(4);
        robot.write("Test1");
        robot.clickOn(searchTestButton);

    }
}