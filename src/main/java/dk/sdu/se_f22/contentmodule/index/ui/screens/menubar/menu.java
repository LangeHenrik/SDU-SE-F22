package ui.screens.menubar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.customs.menuBarParts.menuCustomButtons;
import ui.customs.menuBarParts.underMenu;

import java.util.ArrayList;

public class menu {
    StackPane mainPane;

    VBox vbox;

    underMenu underMenu;
    HBox bar;
    HBox buttons;
    HBox extra;
    HBox Home;

    private double xOffset;
    private double yOffset;

    private menuCustomButtons SQL;
    private menuCustomButtons connected;
    private VBox tempvBox;

    public menu(){
        mainPane = new StackPane();
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(bg);
        mainPane.setPrefSize(750,750);
        screenInitializer();
        xOffset = 0;
        yOffset = 0;
    }

    public StackPane getMainPane(){
        return mainPane;
    }

    public void loop(){
    }

    private void close(){
        System.exit(0);
    }


    public void test2(){
        if(SQL.getClicked()){
            underMenu.getHBox().setVisible(true);
        }else {
            underMenu.getHBox().setVisible(false);
        }
    }

    public void setVbox(){
        if(connected.getClicked()){
            tempvBox.setVisible(true);
        }else {
            tempvBox.setVisible(false);
        }
    }

    public void sheesh(){
        if(underMenu.getMenuButton("SQL").getClicked()){
            System.out.println("clicked SQL");
        }else if(!underMenu.getMenuButton("SQL").getClicked()){
            System.out.println("unclicked SQL");
        }
    }

    private void screenInitializer(){
        vbox = new VBox();
        /////////////////////////////////////////////////

        /////////////////////////////////////////////////
        ArrayList<String> underbarButs = new ArrayList<>();
        underbarButs.add("Recreate Tables");
        underbarButs.add("Add page");
        underbarButs.add("Remove page");
        underbarButs.add("Update page");
        underbarButs.add("SQL");
        underMenu = new underMenu(underbarButs, 13, "#6E644E");
        underMenu.getHBox().setMaxHeight(35);
        underMenu.getHBox().setPrefWidth(750-150);
        underMenu.getHBox().setMaxWidth(750-150);
        underMenu.getHBox().setStyle("-fx-padding: 0px 40px 0px 40px; -fx-background-color: #2E3033; -fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 2px 2px");
        underMenu.getHBox().setAlignment(Pos.CENTER);
        underMenu.getHBox().setPrefHeight(40);
        underMenu.getHBox().setMaxHeight(40);

        HBox temphbox = new HBox();
        temphbox.getChildren().add(underMenu.getHBox());
        HBox temphbox2 = new HBox();
        temphbox2.setPrefWidth(150);

        ArrayList<String> ConnectedButs = new ArrayList<>();
        ConnectedButs.add("Settings");
        ConnectedButs.add("Log out");
        tempvBox = new VBox();
        tempvBox.setMinHeight(100);
        int textSize = 19;

        menuCustomButtons Settings = cusButtonSettings("Settings", 13, "#6E644E", tempvBox);
        menuCustomButtons LO = cusButtonSettings("Log out", 13, "#6E644E", tempvBox);
        tempvBox.setAlignment(Pos.CENTER);
        tempvBox.setStyle("-fx-padding: 0px 10px 0px 10px; -fx-background-color: #2E3033; -fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 2px 2px");
        tempvBox.setVisible(false);

        tempvBox.setPrefWidth(150);
        temphbox.getChildren().add(tempvBox);



        /////////////////////////////////////////////////

        bar = new HBox();
        bar.setAlignment(Pos.CENTER);
        bar.setStyle("-fx-padding: 40px 40px 40px 40px;");
        bar.setStyle("-fx-background-color: #2E3033; -fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 2px 0px 2px 0px");
        bar.setPrefHeight(60);

        /////////////////////////////////////////////////

        Home = new HBox(20);
        Home.setAlignment(Pos.CENTER);
        Home.setPadding(new Insets(0,10, 0,10));
        Home.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 0px 2px");
        cusButtonSettings("Startpage", textSize, "#6E644E", Home);

        /////////////////////////////////////////////////

        buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0,20, 0,20));
        buttons.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 0px 2px");

        /////////////////////////////////////////////////

        extra = new HBox();
        extra.setAlignment(Pos.CENTER);
        extra.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0 0 0 5px");
        extra.setPadding(new Insets(0,10, 0,10));
        extra.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 0px 2px");

        /////////////////////////////////////////////////
        //Buttons

        menuCustomButtons Search = cusButtonSettings("GruSearch", textSize, "#6E644E", buttons);
        SQL = cusButtonSettings("SQL", textSize, "#6E644E", buttons, this, "test2");
        SQL.Style(true);
        connected = cusButtonSettings("Connected", textSize, "#6E644E", extra, this, "setVbox");
        connected.Style(true);

        underMenu.getMenuButton("SQL").getBut().setOnMouseClicked(e-> underMenu.getMenuButton("SQL").clickedEntered(true));

        /////////////////////////////////////////////////


        /////////////////////////////////////////////////

        extra.setPrefWidth(150);
        Home.setPrefWidth(150);
        buttons.setPrefWidth(750-extra.getPrefWidth()-Home.getPrefWidth());
        bar.getChildren().addAll(Home,buttons, extra);
        vbox.getChildren().add(bar);

        vbox.getChildren().add(temphbox);

        vbox.setStyle("-fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 0px 2px 2px 2px");

        mainPane.getChildren().add(vbox);



    }



    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, HBox h){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor);
        h.getChildren().add(temp.getBut());
        temp.getBut().setOnMouseClicked(e-> temp.clickedEntered());
        return temp;
    }

    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, HBox h, Object obj, String methName){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor, obj, methName);
        h.getChildren().add(temp.getBut());
        return temp;
    }

    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, VBox h){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor);
        h.getChildren().add(temp.getBut());
        temp.getBut().setOnMouseClicked(e-> temp.clickedEntered());
        return temp;
    }

    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, VBox h, Object obj, String methName){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor, obj, methName);
        h.getChildren().add(temp.getBut());
        return temp;
    }
}
