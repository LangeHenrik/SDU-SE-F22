package ui.customs;

import javafx.scene.control.TextField;

public class CustomTextField {
    private final TextField textField;
    private final String Text;
    private String defaultValue;

    public CustomTextField(String Text, String defaultValue){
        this.Text = Text;
        this.defaultValue = defaultValue;
        this.textField = new TextField(Text);
        this.Style(30,"B8952BFF");
        this.textField.setOnMouseEntered(e-> OMEntered());
        this.textField.setOnMouseExited(e-> OMExited());
    }

    public String defaultCheck(){
        if(this.textField.getText().equals(Text) || this.textField.getText().equals("")){
            return defaultValue;
        }
        return textField.getText();
    }

    public String getMaskText(){return Text;}

    private void OMEntered(){
        if(this.textField.getText().equals(Text)){
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

    public void Style(int TextSize,String color){
        this.textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffce3b; -fx-font-size: 30px; -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: transparent;");
        this.textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #b8952b; -fx-font-size: 30px; -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: #b8952b;");
        this.textField.setOnMouseEntered(e -> textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #b8952b; -fx-font-size: 30px; -fx-border-radius: 20 20 20 20; -fx-border: solid; -fx-border-color: #b8952b;"));
    }
}
