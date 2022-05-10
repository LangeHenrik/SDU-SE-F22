package ui.screens;
import ui.Logic.switchScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class testScreenTemp {
    StackPane mainPane;

    public testScreenTemp(switchScreen switchScreen){
        mainPane = new StackPane();
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(bg);
        mainPane.setPrefSize(750,750);
        screenInitializer();
    }

    public Scene getMainPane(){
        Scene scene = new Scene(this.mainPane);
        return scene;
    }

    public void loop(){
    }

    private void screenInitializer(){

    }

}
