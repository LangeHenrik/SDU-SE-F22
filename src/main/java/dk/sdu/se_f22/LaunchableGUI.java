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
        String fixedPath = file.getPath().replace("\\", "/");
        String relativePath = fixedPath.split("/dk/sdu/se_f22/")[1];
        String path = "/dk/sdu/se_f22/" + relativePath;
        URL url = getClass().getResource(path);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 600, 400);
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
