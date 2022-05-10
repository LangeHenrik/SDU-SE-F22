package dk.sdu.se_f22.contentmodule.index.ui.customs.menuBarParts;

import javafx.scene.control.Button;

public class menuCustomButtons {
    private final Button but;
    private Boolean clicked;
    private Boolean hover;

    private int TextSize;
    private String TextColor;

    private Object obj;
    private String MethName;

    public Button getBut() {
        return but;
    }


    public menuCustomButtons(String Text, int TextSize, String TextColor){
        this.but = new Button(Text);
        this.clicked = false;
        this.hover = false;
        this.TextSize = TextSize;
        this.TextColor = TextColor;
        this.Style();
        this.getBut().setOnMouseClicked(e-> clickedEntered());
    }

    public void Style(){
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+"px;";
        String border = " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+TextColor+";";
        this.but.setStyle(Style + border+ " -fx-border-color: transparent;");

        this.but.setOnMouseEntered(e -> {
            but.setStyle(Style + border);
            hover = true;
        });

        this.but.setOnMouseExited(e-> {
            but.setStyle(Style + border + " -fx-border-color: transparent;");
            hover = false;
        });
    }


    public void fade(double i){
        this.but.setOpacity(this.but.getOpacity() + i);
    }

    //////////////////////////

    public void StyleMenu(){
        String i = "-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+"px; -fx-border: solid; -fx-border-color: #6E644E; -fx-border-width: 5 5 5 5px;";
        this.but.setStyle(i);
        this.but.setOnMouseEntered(null);
        this.but.setOnMouseExited(null);
    }

    public void StyleMenuExited(){
        Style();
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+"px;";
        this.but.setStyle(Style + " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+TextColor+";");
    }

    //

    public void clickedEntered(){
        this.StyleMenu();
        this.getBut().setOnMouseClicked(e-> clickedExited());
        clicked = true;
    }

    private void clickedExited(){
        this.StyleMenuExited();
        this.getBut().setOnMouseClicked(e-> clickedEntered());
        clicked = false;
    }

    //// with method call
    public menuCustomButtons(String Text, int TextSize, String TextColor, Object obj, String MethName){
        this.but = new Button(Text);
        this.clicked = false;
        this.hover = false;
        this.TextSize = TextSize;
        this.TextColor = TextColor;
        this.obj = obj;
        this.MethName = MethName;
        this.Style(true);
        this.getBut().setOnMouseClicked(e-> clickedEntered(true));
    }

    public void clickedEntered(Boolean b){
        this.StyleMenu();
        this.getBut().setOnMouseClicked(e-> clickedExited(true));
        clicked = true;
        try{
            obj.getClass().getDeclaredMethod(MethName).invoke(obj);
        }catch (Exception e){
            System.out.println("error");
        }
    }

    public void clickedExited(Boolean b){
        this.StyleMenuExited(true);
        this.getBut().setOnMouseClicked(e-> clickedEntered(true));
        clicked = false;
        try{
            obj.getClass().getDeclaredMethod(MethName).invoke(obj);
        }catch (Exception e){
            System.out.println("error");
        }
    }

    public void StyleMenuExited(Boolean b){
        Style(true);
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+"px;";
        this.but.setStyle(Style + " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+TextColor+";");
    }

    public void Style(Boolean b){
        String Style = "-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+"px;";
        String border = " -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: "+TextColor+";";
        this.but.setStyle(Style + border+ " -fx-border-color: transparent;");

        this.but.setOnMouseEntered(e -> {
            but.setStyle(Style + border);
            hover = true;
            try{
                obj.getClass().getDeclaredMethod(MethName).invoke(obj);
            }catch (Exception k){
                System.out.println("error");
            }
        });

        this.but.setOnMouseExited(e-> {
            but.setStyle(Style + border + " -fx-border-color: transparent;");
            hover = false;
            try{
                obj.getClass().getDeclaredMethod(MethName).invoke(obj);
            }catch (Exception k){
                System.out.println("error");
            }
        });
    }
    ////

    public boolean getClicked(){
        return clicked;
    }

    public boolean getHover(){
        return hover;
    }
}

