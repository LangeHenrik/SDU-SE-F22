package dk.sdu.se_f22.searchmodule.onewaysynonyms.presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class OneWayGUITest {

    Scene scene;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OneWayGUI.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("OneWayGUI");
        stage.setScene(scene);
        stage.show();
    }

    private Button getFilterButton(FxRobot robot){
        Optional<Button> onSem1SearchButton = robot.lookup(hasText("filter")).tryQueryAs(Button.class);
        if (onSem1SearchButton.isEmpty())
            fail("A button with text 'filter' could not be found!");
        return onSem1SearchButton.get();
    }

    private Tab getTabs(){
        Node moduelFunctionTab = scene.getRoot().lookup("Module Function");
        return null;
    }

    private TextField getMFInput(){
        return null;
    }






}