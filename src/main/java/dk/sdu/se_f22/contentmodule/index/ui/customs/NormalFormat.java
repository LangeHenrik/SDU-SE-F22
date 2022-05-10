package dk.sdu.se_f22.contentmodule.index.ui.customs;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class NormalFormat {

    public static void Hbox(HBox h){
        for(int i = 0; i < h.getChildren().size(); i++){
            Node b = h.getChildren().get(i);
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffce3b; -fx-font-size: 20px; -fx-border: solid; -fx-border-color: #ffce3b;");
            if(i == 0 && h.getChildren().size() == 1){
                b.setStyle(b.getStyle()+" -fx-border-radius:  20 20 20 20;");

            }else if(i == 0){
                b.setStyle(b.getStyle()+" -fx-border-radius:  20 0 0 20;");

            }else if(i == h.getChildren().size()-1){
                b.setStyle(b.getStyle()+" -fx-border-radius: 0 20 20 0;");

            }
        }
    }
}
