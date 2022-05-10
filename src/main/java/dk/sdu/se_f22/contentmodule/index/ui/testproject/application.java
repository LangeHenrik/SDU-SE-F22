package ui.testproject;

import DB.*;
import ui.Logic.Logic;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.mock.crud;

public class application extends Application {

    private Scene scene;
    private Logic logic;
    private Stage prim;

    private void update(){
        logic.loop();
        prim.setScene(logic.getScene());
        prim.show();
    }

    @Override
    public void start(Stage stage) {
        logic = new Logic();
        prim = new Stage();
        prim.initStyle(StageStyle.UNDECORATED);
        prim.setTitle("DB test interface");
        prim.setResizable(false);
        scene = logic.getScene();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();

        prim.setScene(scene);
        prim.show();
        logic.giveStage(prim);
    }

    public static void main(String[] args) {
        Database db = new Database();

        RecreateTables.recreate(db);

        crud crud = new crud();
        crud.DropTable();
        crud.CreateTable();

        String[] temp = {"vi","tester","tester","om","det","virker"};
        crud.addPage("html side", temp);

        //addPage.add(db, "geforce gtx 3080 ti", 1);
        //addPage.add(db, "geforce gtx 3080",2);
        //addPage.add(db, "geforce gtx 3070",3);
        //addPage.add(db, "radeon rx 6900 xt",4);
        //addPage.add(db, "radeon rx 6700 xt",5);

        //update up = new update(db);
        //String[] temp = {"test", "virker", "det", "geforce", "gtx"};
        //up.updateSingular(1, temp);

        //delete del = new delete(db);
        //int[] k = {1,2,3,4,5};
        //del.delList(k);
        //del.delSingular(1);

        //String[] SearchString = {"geforce", "gtx", "3080"};
        //Search.SearchMethode(SearchString, db);
        //launch();
    }
}