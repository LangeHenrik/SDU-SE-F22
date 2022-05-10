package ui.screens.screens;
import ui.Logic.switchScreen;
import javafx.scene.layout.StackPane;

public class startScreen {
    StackPane mainPane;

    public startScreen(switchScreen switchScreen){
        mainPane = new StackPane();
        screenInitializer();
    }

    public StackPane getMainPane(){
        return mainPane;
    }

    public void loop(){
    }

    private void screenInitializer(){

    }

}
