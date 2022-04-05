package dk.sdu.se_f22.searchmodule.infrastructure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestApplicationAdministrativeGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(TestApplicationAdministrativeGUI.class.getResource("SEM1_Infrastructure_AdministrativeGUI.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplicationAdministrativeGUI.class.getResource("SEM1_Infrastructure_AdministrativeGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
