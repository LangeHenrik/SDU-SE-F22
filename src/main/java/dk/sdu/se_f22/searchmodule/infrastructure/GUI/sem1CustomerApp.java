package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sem1CustomerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(sem1CustomerApp.class.getResource("SEM1customerGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Customer Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
