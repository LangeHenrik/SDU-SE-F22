package dk.sdu.se_f22.contentmodule.index.ui.customs.menuBarParts;

import dk.sdu.se_f22.contentmodule.index.ui.customs.CustomText;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopMenu {
    private HBox topMenu;
    private String WindowName;
    private CustomText test;
    private Stage stage;
    private double xOffset;
    private double yOffset;

    public TopMenu(String WindowName, String bgColor, String TextColor){
        this.WindowName = WindowName;
        topMenu = new HBox();
        topMenu.setStyle("-fx-padding: 0px 20px 0px 20px; -fx-background-color: "+bgColor+";");
        topMenu.setAlignment(Pos.CENTER);
        HBox SystemButtons = new HBox();
        HBox winName = new HBox();

        menuCustomButtons min = cusButtonSettings("Minimize", 12, TextColor, SystemButtons);
        min.getBut().setOnMouseClicked(e-> stage.setIconified(true));
        menuCustomButtons close = cusButtonSettings("Close", 12, TextColor, SystemButtons);
        close.getBut().setOnMouseClicked(e-> System.exit(0));

        test = new CustomText(WindowName, 12, TextColor);
        winName.getChildren().add(test.getCusText());

        winName.setMinWidth(355);
        winName.setAlignment(Pos.CENTER_LEFT);
        topMenu.getChildren().add(winName);

        SystemButtons.setMinWidth(355);
        SystemButtons.setAlignment(Pos.CENTER_RIGHT);
        topMenu.getChildren().add(SystemButtons);

        /////////////////////////////////////////////////

        topMenu.setOnMousePressed(e->{
            xOffset = stage.getX() - e.getScreenX();
            yOffset = stage.getY() - e.getScreenY();
        });
        topMenu.setOnMouseDragged(e-> {
            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });
    }

    private menuCustomButtons cusButtonSettings(String text, int textSize, String textColor, Pane h){
        menuCustomButtons temp = new menuCustomButtons(text, textSize, textColor);
        h.getChildren().add(temp.getBut());
        return temp;
    }

    public HBox getTopMenu(){
        return topMenu;
    }

    public void changeName(String i){
        this.WindowName = i;
        test.getCusText().setText(WindowName);
    }

    public  void setStage(Stage stage){
       this.stage = stage;
    }
}
