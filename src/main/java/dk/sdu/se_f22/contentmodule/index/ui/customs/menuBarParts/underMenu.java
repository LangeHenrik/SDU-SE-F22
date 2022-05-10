package ui.customs.menuBarParts;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;

public class underMenu {
    private HashMap<String, menuCustomButtons> buttons;
    private HBox hBox;

    public underMenu(ArrayList<String> buttonsNames, int TextSize, String Color){
        this.hBox = new HBox();
        hBox.setVisible(false);
        buttons = new HashMap<>();
        for(String i : buttonsNames){
            addButton(i, TextSize, Color);
        }
    }

    public HBox getHBox() {
        return hBox;
    }

    public menuCustomButtons getMenuButton(String Name) {
        return buttons.get(Name);
    }

    public Button getButton(String Name) {
        return buttons.get(Name).getBut();
    }

    public void addButton(String Name, int TextSize, String Color){
        buttons.put(Name, new menuCustomButtons(Name,TextSize,Color));
        buttons.get(Name).Style();
        buttons.get(Name).getBut().setOnMouseClicked(e-> buttons.get(Name).clickedEntered());
        hBox.getChildren().add(buttons.get(Name).getBut());
    }
}
