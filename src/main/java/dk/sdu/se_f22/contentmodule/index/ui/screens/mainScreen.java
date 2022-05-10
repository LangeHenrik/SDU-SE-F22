package dk.sdu.se_f22.contentmodule.index.ui.screens;

import dk.sdu.se_f22.contentmodule.index.DB.Database;
import dk.sdu.se_f22.contentmodule.index.crud.GruSearch.Search;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import dk.sdu.se_f22.contentmodule.index.ui.customs.CustomButton;
import dk.sdu.se_f22.contentmodule.index.ui.customs.CustomTextField;

public class mainScreen {
    private StackPane mainPane;
    private Database db;

    private CustomTextField searchfield;
    private CustomButton SearchButton;

    public mainScreen(Database db){
        this.db = db;
        mainPane = new StackPane();
        screeninitializer();
    }

    public StackPane getMainPane(){
        return this.mainPane;
    }

    private void screeninitializer(){
        HBox test = new HBox();
        test.setAlignment(Pos.CENTER);
        searchfield = new CustomTextField("GruSearch", "");
        SearchButton = new CustomButton("GruSearch", 30);
        SearchButton.getConBut().setOnMouseClicked(e-> searchField());

        test.getChildren().add(searchfield.getTextField());
        test.getChildren().add(SearchButton.getConBut());
        test.setStyle("-fx-background-color: #010203;");
        mainPane.getChildren().add(test);
        mainPane.setPrefSize(750,750);
    }

    private void searchField(){
        if(!(searchfield.getTextField().getText().equals(searchfield.getMaskText())) || !(searchfield.getTextField().getText().equals(""))){
            String temp[] = searchfield.getTextField().getText().split(" ");
            Search.SearchMethode(temp, db);
            //ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(temp));
            //interfaceSearch.Search(tempArray);
        }
    }
}
