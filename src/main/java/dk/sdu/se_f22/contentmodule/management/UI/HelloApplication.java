package dk.sdu.se_f22.contentmodule.management.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/sdu/se_f22/contentmodule/management/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 560);
        stage.setTitle("Content Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
