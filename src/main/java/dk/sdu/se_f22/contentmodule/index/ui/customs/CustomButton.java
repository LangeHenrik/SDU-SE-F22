package ui.customs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
public class CustomButton {
    private final Button conBut;

    public CustomButton(String Text){
        this.conBut = new Button(Text.substring(0,1).toUpperCase() + Text.substring(1));
        this.Style(48,"#ffce3b", "#b8952b");
    }

    public Button getConBut() {
        return conBut;
    }

    public CustomButton(String Text, int TextSize){
        this.conBut = new Button(Text);
        this.Style(TextSize,"#ffce3b", "#b8952b");
    }

    public CustomButton(String Text, int TextSize, String Textcolor){
        this.conBut = new Button(Text);
        this.Style(TextSize,Textcolor);
    }

    public void Style(int TextSize,String color){
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+color+"; -fx-font-size: "+TextSize+"px;";
        this.conBut.setStyle(Style);
        this.conBut.setOnMouseEntered(e -> conBut.setStyle(Style + " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+color+";"));
        this.conBut.setOnMouseExited(e -> conBut.setStyle(Style));
    }

    public void Style(int TextSize, String color,String color2){
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+color+"; -fx-font-size: "+TextSize+"px; ";
        this.conBut.setStyle(Style);
        this.conBut.setOnMouseEntered(e -> conBut.setStyle(Style + "-fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+color2+";"));
        this.conBut.setOnMouseExited(e -> conBut.setStyle(Style));
    }

    public void fade(double i){
        this.conBut.setOpacity(this.conBut.getOpacity() + i);
    }

    //////////////////////////

    public void Style(int TextSize, String color, HBox h){
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+color+"; -fx-font-size: "+TextSize+"px;";
        this.conBut.setStyle(Style);

        this.conBut.setOnMouseEntered(e -> {
            conBut.setStyle(Style + " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+color+";");
            h.setPadding(new Insets(0,19,0,19));
        });

        this.conBut.setOnMouseExited(e-> {
            conBut.setStyle(Style);
            h.setPadding(new Insets(0,20,0,20));
        });
    }

    public void StyleMenu(int TextSize, String color){
        String i = "-fx-background-color: transparent; -fx-text-fill: "+color+"; -fx-font-size: "+TextSize+"px; -fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 5 5 5 5px;";
        this.conBut.setStyle(i);
        this.conBut.setOnMouseEntered(e -> this.conBut.setStyle(i));
        this.conBut.setOnMouseExited(e -> this.conBut.setStyle(i));
    }

    public void StyleMenuExited(int TextSize, String color, HBox h){
        Style(TextSize, color);
        h.setPadding(new Insets(0,19,0,19));
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+color+"; -fx-font-size: "+TextSize+"px;";
        this.conBut.setStyle(Style + " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+color+";");
    }

    //

    public void clickedEntered(CustomButton o, HBox h, int textSize){
        o.StyleMenu(textSize, "#6E644E");
        h.setPadding(new Insets(0,15,0,15));
        o.getConBut().setOnMouseClicked(e-> clickedExited(o, h,textSize));
    }
    private void clickedExited(CustomButton o, HBox h, int textSize){
        o.StyleMenuExited(textSize, "#6E644E", h);
        h.setPadding(new Insets(0,19,0,19));
        o.getConBut().setOnMouseClicked(e-> clickedEntered(o, h,textSize));
    }
}

