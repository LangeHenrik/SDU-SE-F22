package dk.sdu.se_f22.contentmodule.index.ui.screens;

import dk.sdu.se_f22.contentmodule.index.DB.Database;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import dk.sdu.se_f22.contentmodule.index.ui.customs.*;

public class screentest {
    private boolean startButton;
    private StackPane startScreenStackPane;
    private CustomButton customconnectBut;
    private CustomButton advancedButton;

    private boolean ConnectionStatus;
    private boolean Recon;
    private boolean loginBoolean;
    private boolean conAttempt;
    private boolean finished;

    CustomTextField user;
    CustomTextField pass;
    CustomTextField ipcusfield;
    CustomTextField portcusfield;
    private CustomTextField databaseName;

    int butmovecounter = 0;

    private HBox Buttons;

    GridPane gridPane;

    public screentest() {
        varSetter();
        screeninitializer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    public void loop(){
        if(this.getBoolState()){
            this.connected();
        }
        if(loginBoolean){
            this.login();
        }

        if(butmovecounter != 0){
            Buttonsmove();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    private void varSetter(){
        this.customconnectBut = new CustomButton("Connect");
        this.advancedButton = new CustomButton("advanced", 25);
        this.user = new CustomTextField("Username", "postgres");
        this.pass = new CustomTextField("Password", "test1234");
        this.ipcusfield = new CustomTextField("ip", "localhost");
        this.portcusfield = new CustomTextField("Port", "5432");
        this.databaseName = new CustomTextField("Database Name","postgres");

        this.user.getTextField().setVisible(false);
        this.pass.getTextField().setVisible(false);
        this.ipcusfield.getTextField().setVisible(false);
        this.portcusfield.getTextField().setVisible(false);
        this.databaseName.getTextField().setVisible(false);

        this.ConnectionStatus = false;
        this.Recon = false;
        this.loginBoolean = false;
        this.conAttempt = false;
        this.startButton = false;
        this.finished = false;
        advancedButton.getConBut().setVisible(false);
    }

    public void screeninitializer(){
        varSetter();
        StackPane stackpane = new StackPane();

        VBox bg = new VBox();
        bg.setAlignment(Pos.CENTER);
        bg.setStyle("-fx-background-color: #010203;");

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #010203;");
        vBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setPrefHeight(100);
        gridPane.setAlignment(Pos.CENTER);
        user.getTextField().setMaxWidth(200);
        pass.getTextField().setMaxWidth(200);

        advancedButton.getConBut().setOnMouseClicked(e -> advancedLoginSettings());
        customconnectBut.getConBut().setOnMouseClicked(e -> setConAttempt(true));
        customconnectBut.getConBut().setTranslateX(-75);


        HBox userpassword = new HBox();
        userpassword.getChildren().add(user.getTextField());
        userpassword.getChildren().add(pass.getTextField());
        gridPane.add(userpassword,0,1);

        VBox testv = new VBox();
        testv.getChildren().add(gridPane);
        testv.setAlignment(Pos.CENTER);

        ipcusfield.getTextField().setMaxWidth(290);
        portcusfield.getTextField().setMaxWidth(110);

        HBox advanced = new HBox();
        advanced.getChildren().add(ipcusfield.getTextField());
        advanced.getChildren().add(portcusfield.getTextField());
        gridPane.add(advanced,0,2);

        HBox tempHbox = new HBox();
        tempHbox.setAlignment(Pos.CENTER);
        tempHbox.getChildren().add(this.databaseName.getTextField());
        gridPane.add(tempHbox,0,3);

        Buttons = new HBox();
        Buttons.setAlignment(Pos.CENTER);
        Buttons.getChildren().add(advancedButton.getConBut());
        Buttons.getChildren().add(customconnectBut.getConBut());
        Buttons.setTranslateY(-120);
        gridPane.add(Buttons, 0,4);


        stackpane.getChildren().add(bg);
        stackpane.getChildren().add(testv);
        stackpane.setPrefSize(750,750);
        startScreenStackPane = stackpane;
    }

    public void advancedLoginSettings(){
        if(ipcusfield.getTextField().isVisible() && portcusfield.getTextField().isVisible()){
            advancedButton.getConBut().setText("advanced");
            butmovecounter = 2;
            ipcusfield.getTextField().setVisible(false);
            portcusfield.getTextField().setVisible(false);
            databaseName.getTextField().setVisible(false);
        }else {
            advancedButton.getConBut().setText("simplify");
            customconnectBut.Style(25, "#ffce3b");
            butmovecounter = 1;
        }
    }

    private void Buttonsmove (){
        int min = -121;
        int max = 16;

        if(Buttons.getTranslateY() > min && Buttons.getTranslateY() < max){
            if(butmovecounter == 1){
                Buttons.setTranslateY(Buttons.getTranslateY()+1);
            }else if(butmovecounter == 2){
                Buttons.setTranslateY(Buttons.getTranslateY()-1);
            }
        }else{
            if(Buttons.getTranslateY() == min){
                Buttons.setTranslateY(min+1);
            }else if(Buttons.getTranslateY() == max){
                Buttons.setTranslateY(max-1);
                ipcusfield.getTextField().setVisible(true);
                portcusfield.getTextField().setVisible(true);
                databaseName.getTextField().setVisible(true);
            }
            System.out.println(Buttons.getTranslateY());
            butmovecounter = 0;
        }

    }

    public Database dbcon(){
        setConAttempt(false);
        Database db = new Database(ipcusfield.defaultCheck(), portcusfield.defaultCheck(), databaseName.defaultCheck(), user.defaultCheck(), pass.defaultCheck());
        if(db.isConStatus()){
            conchange();
        }else {
            conchangeerror();
        }

        return db;
    }

    public void setConnectionStatus(){
        ConnectionStatus = false;
    }

    /*
    getters
     */

    public StackPane getStartScreenStackPane() {
        return startScreenStackPane;
    }

    public boolean getConStatus() {
        return ConnectionStatus;
    }

    public boolean getBoolState() {
        return startButton;
    }

    public boolean isConAttempt() {
        return conAttempt;
    }

    public boolean isFinished() {
        return finished;
    }
    public void setFinished(Boolean finished){
        this.finished = finished;
    }

    /*
    setters
     */

    private void setConAttempt(boolean conAttempt) {
        this.conAttempt = conAttempt;
    }

    public void setBoolState(boolean state) {
        this.startButton = state;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void conchangeerror(){
        customconnectBut.Style(25, "#99182e", "#99182e");
        customconnectBut.getConBut().setText("Error Connecting");
        loginBoolean=true;
        customconnectBut.getConBut().setOnMouseClicked(e ->setConAttempt(true));

    }

    public void login(){
        if(this.customconnectBut.getConBut().getOpacity() > -1.1 && this.customconnectBut.getConBut().getOpacity() < 1.1){
            customconnectBut.fade(-0.04);
            advancedButton.fade(-0.04);
        }else {
            user.getTextField().setVisible(true);
            pass.getTextField().setVisible(true);
            customconnectBut.Style(25, "#ffce3b");
            customconnectBut.getConBut().setText("Login");
            customconnectBut.getConBut().setOpacity(1);
            advancedButton.getConBut().setOpacity(1);
            loginBoolean = false;
            customconnectBut.getConBut().setTranslateX(0);
            advancedButton.getConBut().setVisible(true);
            Buttons.setTranslateX(-22);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void connected(){
        if(this.customconnectBut.getConBut().getOpacity() > -1.1 && this.customconnectBut.getConBut().getOpacity() < 1.1){
            customconnectBut.fade(-0.04);
            this.gridPane.setOpacity(this.gridPane.getOpacity()-0.04);
        }else {
            this.customconnectBut.getConBut().setOpacity(1);
            customconnectBut.Style(15, "#3ba501", "#3ba501");
            this.customconnectBut.getConBut().setTranslateX(310);
            this.customconnectBut.getConBut().setTranslateY(-335);
            this.customconnectBut.getConBut().setOpacity(1);
            this.customconnectBut.getConBut().setOnMouseClicked(e -> this.Recon = true);
            setBoolState(false);
            setFinished(true);
        }
    }

    public void conchange(){
        setBoolState(true);
        ConnectionStatus = true;
        customconnectBut.Style(25, "#3ba501", "#3ba501");
        customconnectBut.getConBut().setText("Connected");
    }

    public Boolean getRecon(){
        return Recon;
    }
}