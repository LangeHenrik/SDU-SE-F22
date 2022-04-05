package dk.sdu.se_f22.productmodule.presentation;

import dk.sdu.se_f22.productmodule.management.IProductManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends javafx.application.Application {
    public static IProductManager productManager;

    @Override
    public void start(Stage stage) throws Exception {
        Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader, 800,600);
        stage.setTitle("Product Module");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}