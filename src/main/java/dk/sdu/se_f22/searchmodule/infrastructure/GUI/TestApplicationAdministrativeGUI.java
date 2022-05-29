package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestApplicationAdministrativeGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(getClass().getResource("SEM1_Infrastructure_AdministrativeGUI.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SEM1_Infrastructure_AdministrativeGUI.fxml"));
        Parent root = fxmlLoader.load();
        SEM1InfrastructureAdministrativeGUIController controller = fxmlLoader.getController();
        controller.setSearchModule((SearchModuleImpl) SearchModuleImpl.createDefaultSearchModule());
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

