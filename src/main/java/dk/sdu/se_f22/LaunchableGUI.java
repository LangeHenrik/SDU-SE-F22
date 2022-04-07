package dk.sdu.se_f22;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LaunchableGUI {
    File file;

    public LaunchableGUI(File file) {
        this.file = file;
    }

    public void launch() throws IOException {
        URL url = getClass().getResource(file.getPath());

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 250, 150);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(200);
        stage.setMinWidth(340);
        stage.show();
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
