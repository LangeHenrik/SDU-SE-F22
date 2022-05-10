package ui.screens.LoginScreens;
import DB.Database;
import ui.Logic.switchScreen;
import ui.customs.CustomText;
import ui.customs.CustomTextFieldRefined;
import ui.customs.menuBarParts.TopMenu;
import ui.customs.menuBarParts.menuCustomButtons;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class SimpleLoginScreen {
    StackPane mainPane;

    private menuCustomButtons Login;
    private menuCustomButtons advanced;
    private CustomTextFieldRefined user;
    private CustomTextFieldRefined pass;
    private CustomText LoginStat;

    private switchScreen switchScreen;

    public SimpleLoginScreen(switchScreen switchScreen){
        mainPane = new StackPane();
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(bg);
        mainPane.setPrefSize(750,750);
        this.switchScreen = switchScreen;
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

        Login = cusButtonSettings("Login", 20, "#6E644E", this, "LoginCheck", testVbox);
        LoginStat = new CustomText("Login Successful", 13, "#6E644E");
        advanced = cusButtonSettings("advanced", 14, "#6E644E", this, "advanceLogin", testVbox);
        LoginStat.getCusText().setVisible(false);
        testVbox.getChildren().add(LoginStat.getCusText());

        TopMenu top = switchScreen.getTopMenu();

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
            db = new Database(user.defaultCheck(), pass.defaultCheck());
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

    public void advanceLogin(){
        if(advanced.getClicked()){
            switchScreen.setTheInt(1);
            advanced.clickedExited(true);
        }
    }
}
