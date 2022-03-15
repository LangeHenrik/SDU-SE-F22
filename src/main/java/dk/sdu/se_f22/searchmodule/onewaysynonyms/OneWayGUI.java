package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OneWayGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OneWayGUI.class.getResource("OneWayGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),320,240);
        primaryStage.setTitle("OneWayGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
