package ui.customs;

import javafx.scene.control.TextField;

public class CustomTextFieldRefined {
    private final TextField textField;
    private final String Text;
    private String defaultValue;
    private int TextSize;
    private String TextColor;

    public CustomTextFieldRefined(String Text, String defaultValue, int TextSize, String TextColor){
        this.Text = Text;
        this.defaultValue = defaultValue;
        this.textField = new TextField(Text);
        this.Style();
        this.textField.setOnMouseEntered(e-> OMEntered());
        this.textField.setOnMouseExited(e-> OMExited());
        this.TextSize = TextSize;
        this.TextColor = TextColor;
        this.Style();
    }

    public String defaultCheck(){
        if(this.textField.getText().equals(Text) || this.textField.getText().equals("")){
            return defaultValue;
        }
        return textField.getText();
    }

    public String getMaskText(){return Text;}

    private void OMEntered(){
        if(this.textField.getText().trim().equals(Text)){
            this.textField.setText("");
        }
    }

    private void OMExited(){
        if(this.textField.getText().equals("")){
            this.textField.setText(Text);
        }
    }

    public TextField getTextField(){
        return this.textField;
    }

    public void Style(){
        this.textField.setStyle("-fx-background-color: transparent; -fx-text-fill: "+TextColor+"; -fx-font-size: "+TextSize+" px; -fx-border-radius: 5; -fx-border: solid; -fx-border-color: "+TextColor+";");
    }
}
