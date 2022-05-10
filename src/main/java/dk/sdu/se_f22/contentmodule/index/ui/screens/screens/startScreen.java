package dk.sdu.se_f22.contentmodule.index.ui.screens.screens;
import dk.sdu.se_f22.contentmodule.index.ui.Logic.switchScreen;
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
