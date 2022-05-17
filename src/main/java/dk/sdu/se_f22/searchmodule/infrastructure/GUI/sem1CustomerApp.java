package dk.sdu.se_f22.searchmodule.infrastructure.GUI;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sem1CustomerApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SEM1customerGUI.fxml"));
        Parent root = fxmlLoader.load();
        SEM1customerGUIController controller = fxmlLoader.getController();
        controller.setSearchModule((SearchModuleImpl) SearchModuleImpl.createDefaultSearchModule());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Customer Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
