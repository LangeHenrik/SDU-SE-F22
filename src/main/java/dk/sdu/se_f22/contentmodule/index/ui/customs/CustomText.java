package dk.sdu.se_f22.contentmodule.index.ui.customs;

import javafx.scene.text.Text;

public class CustomText {
    Text cusText;

    public CustomText(String Text, int textSize){
        this.cusText = new Text(Text);
        this.Style(textSize,"B8952BFF");
    }

    public CustomText(String Text, int textSize, String TextColor){
        this.cusText = new Text(Text);
        this.Style(textSize,TextColor);
    }

    public Text getCusText(){return cusText;}

    public void Style(int TextSize,String color){
        this.cusText.setStyle("-fx-fill: "+color+"; -fx-font-size: "+TextSize+"px;");
    }

}
