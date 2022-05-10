package dk.sdu.se_f22.contentmodule.index.ui.screens.base;

import dk.sdu.se_f22.contentmodule.index.ui.Logic.switchScreen;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class BaseScreen {
    StackPane mainPane;
    BorderPane borderPane;
    switchScreen switchScreen;

    public BaseScreen(switchScreen switchScreen){
        mainPane = new StackPane();
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(bg);
        mainPane.setPrefSize(750,750);
        this.switchScreen = switchScreen;
        screenInitializer();
    }

    public Scene getMainPane(){
        Scene scene = new Scene(this.mainPane);
        return scene;
    }

    public void loop(){
    }

    private void screenInitializer(){
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 2px 2px");
        borderPane.setTop(switchScreen.getTopMenu().getTopMenu());
        mainPane.getChildren().add(borderPane);
    }

    public void setCenter(StackPane pane){
        this.borderPane.setCenter(pane);
    }
}
