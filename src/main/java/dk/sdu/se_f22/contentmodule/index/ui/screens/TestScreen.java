package ui.screens;

import ui.customs.NormalFormat;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;

public class TestScreen {
    private StackPane mainPane;
    private Button testBut3;

    private HBox test;
    private HBox test2;

    private boolean testB = true;

    public TestScreen(){
        this.mainPane = new StackPane();
        screeninitializer();
    }

    public Scene getMainPane(){
        Scene scene = new Scene(this.mainPane);
        return scene;
    }

    public void loop(){
        if(testB){
            testWidth();
        }
    }

    public void testWidth(){
        testB = false;
        double k = 0;
        for(int i = 0; i < test.getChildren().size(); i++){
            Node b = test.getChildren().get(i);
            k += b.getBoundsInParent().getWidth();
        }
        testBut3.setPrefWidth(k);
        testBut3.setPrefHeight(test.getChildren().get(0).getBoundsInParent().getHeight());
    }

    private void screeninitializer(){
        Pane bg = new Pane();
        bg.setStyle("-fx-background-color: #010203;");

        test = new HBox();
        test.setAlignment(Pos.CENTER);

        test.getChildren().add(new Button("Login"));
        test.getChildren().add(new Button("connect"));
        test.getChildren().add(new Button("register"));

        testBut3 = new Button();
        testBut3.setOnMouseExited(e-> but3Exited());

        test2 = new HBox();
        test2.setAlignment(Pos.CENTER);
        test2.getChildren().add(testBut3);
        test2.setVisible(false);
        NormalFormat.Hbox(test2);

        mainPane.getChildren().add(bg);
        mainPane.getChildren().add(test);
        mainPane.getChildren().add(test2);
        mainPane.setPrefSize(750,750);

        System.out.println(mainPane.getChildren().get(1).getBoundsInParent().getWidth());

        NormalFormat.Hbox(test);
        for (Node b: test.getChildren()) {
            b.setOnMouseEntered(e-> but3(b.toString().split("'")[1]));
        }
    }

    private void but3(String i){
        test2.setVisible(true);
        testBut3.setText(i);

        test.setVisible(false);
    }

    private void but3Exited(){
        test2.setVisible(false);
        test.setVisible(true);
    }
}
