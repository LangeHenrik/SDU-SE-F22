package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks.MockBrandIndexingModule;
import dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks.MockProductIndexingModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.util.NodeQueryUtils.hasId;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
public class SEM1customerGUIControllerTest {
    Scene scene;
    SEM1customerGUIController controller;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SEM1customerGUIController.class.getResource("SEM1customerGUI.fxml"));

        // Insert mock indexing modules
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.getSearchModule().addIndexingModule(new MockBrandIndexingModule());
        controller.getSearchModule().addIndexingModule(new MockProductIndexingModule());

        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    private Button getOnSem1SearchButton(FxRobot robot) {
        Optional<Button> onSem1SearchButton = robot.lookup(hasText("Search")).tryQueryAs(Button.class);
        if (onSem1SearchButton.isEmpty())
            fail("A button with text 'Search' could not be found!");
        return onSem1SearchButton.get();
    }

    private TextArea getSEM1customerText() {
        Node SEM1customerText = scene.getRoot().lookup("SEM1customerText");
        if (SEM1customerText == null)
            fail("No TextArea could found!");
        return (TextArea) SEM1customerText;
    }

    private TextField getSEM1customerSearchBar(FxRobot robot) {
        Optional<TextField> onSem1SearchBar = robot.lookup(hasId("SEM1customerSearchBar")).tryQueryAs(TextField.class);
        if (onSem1SearchBar.isEmpty())
            fail("No TextField could found!");
        return onSem1SearchBar.get();
    }

    @Test
    void onSem1SearchButton(FxRobot robot) {
        Button search = getOnSem1SearchButton(robot);
        TextField searchBar = getSEM1customerSearchBar(robot);

        robot.clickOn(searchBar);
        robot.write("Hello1");
        robot.clickOn(search);
        robot.sleep(1000);

        assertEquals("1", controller.getBrandHitsCounter().getText());
        assertEquals("0", controller.getProductHitsCounter().getText());

        searchBar.clear();
        robot.clickOn(searchBar);
        robot.write("Yeet");
        robot.clickOn(search);
        robot.sleep(1000);

        assertEquals("0", controller.getBrandHitsCounter().getText());
        assertEquals("0", controller.getProductHitsCounter().getText());


        searchBar.clear();
        robot.clickOn(searchBar);
        robot.write("Test1");
        robot.clickOn(search);
        robot.sleep(1000);

        assertEquals("0", controller.getBrandHitsCounter().getText());
        assertEquals("1", controller.getProductHitsCounter().getText());


        searchBar.clear();
        robot.clickOn(searchBar);
        robot.write("Test1 Hello1");
        robot.clickOn(search);
        robot.sleep(1000);

        assertEquals("1", controller.getBrandHitsCounter().getText());
        assertEquals("1", controller.getProductHitsCounter().getText());
    }
}