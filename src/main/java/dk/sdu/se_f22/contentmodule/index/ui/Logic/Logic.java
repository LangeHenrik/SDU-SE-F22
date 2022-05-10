package dk.sdu.se_f22.contentmodule.index.ui.Logic;
import dk.sdu.se_f22.contentmodule.index.DB.Database;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import dk.sdu.se_f22.contentmodule.index.ui.screens.LoginScreens.AdvancedLoginScreen;
import dk.sdu.se_f22.contentmodule.index.ui.screens.LoginScreens.SimpleLoginScreen;
import dk.sdu.se_f22.contentmodule.index.ui.screens.base.BaseScreen;
import dk.sdu.se_f22.contentmodule.index.ui.screens.screens.startScreen;

public class Logic {
    private Database db;
    private Boolean updateStackPane;

    private AdvancedLoginScreen als;
    private SimpleLoginScreen sls;
    private BaseScreen base;
    private startScreen startScreen;

    private Scene scene;

    private switchScreen switchInt;

    public Logic() {
        switchInt = new switchScreen("#6E644E", "#2E3033");
        switchInt.setTheInt(-1);
        base = new BaseScreen(switchInt);
        als = new AdvancedLoginScreen(switchInt);
        sls = new SimpleLoginScreen(switchInt);
        startScreen = new startScreen(switchInt);
        Object test = als;

        try{
            base.setCenter((StackPane) test.getClass().getDeclaredMethod("getMainPane").invoke(test));
        }catch (Exception e){
        }

        scene = base.getMainPane();

        updateStackPane = false;

    }

    public void giveStage(Stage prim){
        switchInt.giveStage(prim);
        switchInt.setTheInt(-2);
    }


    public Scene getScene() {
        return scene;
    }

    public void loop(){

        switch (switchInt.getTheInt()){
            case 1:
                base.setCenter(als.getMainPane());
                break;
            case 2:
                base.setCenter(sls.getMainPane());
                break;
            case 3:
                base.setCenter(startScreen.getMainPane());
                break;
        }
        switchInt.setTheInt(0);
    }

    public Boolean updateStackPane(){
        return updateStackPane;
    }

    public void setStackPaneUpdate(Boolean b){
        this.updateStackPane = b;
    }
}
