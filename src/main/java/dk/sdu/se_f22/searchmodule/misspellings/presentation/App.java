package dk.sdu.se_f22.searchmodule.misspellings.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("misspellings.fxml"));
        Scene scene = new Scene(fxmlLoader, 800,600);
        stage.setTitle("Misspellings");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
