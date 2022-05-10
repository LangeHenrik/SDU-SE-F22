package dk.sdu.se_f22.contentmodule.index.ui.screens.LoginScreens;

import dk.sdu.se_f22.contentmodule.index.DB.Database;
import dk.sdu.se_f22.contentmodule.index.ui.Logic.switchScreen;
import dk.sdu.se_f22.contentmodule.index.ui.customs.CustomText;
import dk.sdu.se_f22.contentmodule.index.ui.customs.CustomTextFieldRefined;
import dk.sdu.se_f22.contentmodule.index.ui.customs.menuBarParts.menuCustomButtons;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdvancedLoginScreen {
    StackPane mainPane;

    private menuCustomButtons Login;
    private menuCustomButtons simple;
    private CustomTextFieldRefined dbName;
    private CustomTextFieldRefined port;
    private CustomTextFieldRefined ip;
    private CustomTextFieldRefined user;
    private CustomTextFieldRefined pass;
    private CustomText LoginStat;

    private switchScreen switchScreen;


    public AdvancedLoginScreen(switchScreen switchInt){
        mainPane = new StackPane();
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(bg);
        mainPane.setPrefSize(750,750);
        switchScreen = switchInt;
        screenInitializer();
    }

    public StackPane getMainPane(){
        return mainPane;
    }

    public void loop(){
    }

    private void screenInitializer(){

        /////////////////////////////////////////////////
        VBox testVbox = new VBox();
        testVbox.setMaxSize(250,300);
        testVbox.setAlignment(Pos.CENTER);
        testVbox.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 2px 2px 2px 2px; -fx-padding: 20px 20px 20px 20px; -fx-background-color: #2E3033;");
        testVbox.setSpacing(20);

        user = new CustomTextFieldRefined("username", "postgres", 15, "#6E644E");
        testVbox.getChildren().add(user.getTextField());

        pass = new CustomTextFieldRefined("password", "test1234", 15, "#6E644E");
        testVbox.getChildren().add(pass.getTextField());

        dbName = new CustomTextFieldRefined("Database Name", "postgres", 15, "#6E644E");
        testVbox.getChildren().add(dbName.getTextField());

        ip = new CustomTextFieldRefined("ip", "localhost", 15, "#6E644E");
        testVbox.getChildren().add(ip.getTextField());

        port = new CustomTextFieldRefined("Port", "5432", 15, "#6E644E");
        testVbox.getChildren().add(port.getTextField());

        Login = cusButtonSettings("Login", 20, "#6E644E", this, "LoginCheck", testVbox);
        LoginStat = new CustomText("Login Successful", 13, "#6E644E");
        simple = cusButtonSettings("simple", 14, "#6E644E", this, "simpleLogin", testVbox);
        LoginStat.getCusText().setVisible(false);
        testVbox.getChildren().add(LoginStat.getCusText());
        mainPane.getChildren().add(testVbox);
    }

    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, Object obj, String MethName, Pane h){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor, obj, MethName);
        h.getChildren().add(temp.getBut());
        return temp;
    }

    public void LoginCheck(){
        Database db;
        if(Login.getClicked()){
            db = new Database(ip.defaultCheck(), port.defaultCheck(), dbName.defaultCheck(), user.defaultCheck(), pass.defaultCheck());
            if(db.isConStatus()){
                LoginStat.getCusText().setVisible(true);
                LoginStat.getCusText().setText("Login successful");
                switchScreen.setTheInt(3);
            }else {
                Login.clickedExited(true);
                LoginStat.getCusText().setVisible(true);
                LoginStat.getCusText().setText("Login failed");
            }
        }else {
            LoginStat.getCusText().setVisible(false);
        }
    }

    public void simpleLogin(){
        if(simple.getClicked()){
            switchScreen.setTheInt(2);
            simple.clickedExited(true);
        }
    }
}
