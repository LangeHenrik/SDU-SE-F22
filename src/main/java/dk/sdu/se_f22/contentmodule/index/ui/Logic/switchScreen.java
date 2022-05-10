package ui.Logic;

import ui.customs.menuBarParts.TopMenu;
import javafx.stage.Stage;

public class switchScreen {
    private int TheInt;
    private Stage stage;
    private TopMenu topmenu;

    public switchScreen(String bgColor, String TextColor){
        topmenu = new TopMenu("main", bgColor, TextColor);
        TheInt = 0;
    }

    public int getTheInt() {
        return TheInt;
    }

    public void giveStage(Stage stage){
        topmenu.setStage(stage);
    }

    public TopMenu getTopMenu(){
        return topmenu;
    }

    public void setTheInt(int theInt) {
        TheInt = theInt;
    }
}
