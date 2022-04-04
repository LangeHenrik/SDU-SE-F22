package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class OneWayGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OneWayGUI.class.getResource("OneWayGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("OneWayGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
