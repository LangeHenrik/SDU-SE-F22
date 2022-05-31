package dk.sdu.se_f22.productmodule.index;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductIndexController {

    @FXML
    private void onSearchButton1(){
        String searchString = searchField1.getText();
        homepage.setVisible(false);
        resultPage.setVisible(true);
        searchResults.getItems().add("*Result for: " + searchString + "*");
        for(Product p : showResult(searchString)){
         searchResults.getItems().add("Name: " + p.getName() + " Hits: " + p.getHitNum());
        }
    }

    @FXML
    private void onSearchButton2(){
        String searchString = searchField2.getText();
        searchResults.getItems().clear();
        searchResults.getItems().add("*Result for: " + searchString + "*");
        for(Product p : showResult(searchString)){
            searchResults.getItems().add("Name: " + p.getName() + " Hits: " + p.getHitNum());
        }
    }

    @FXML
    private void onFeelingLucky(){
        homepage.setVisible(false);
        luckyPage.setVisible(true);
    }

    @FXML
    private AnchorPane homepage;

    @FXML
    private AnchorPane resultPage;

    @FXML
    private AnchorPane luckyPage;

    @FXML
    private TextField searchField1;

    @FXML
    private TextField searchField2;

    @FXML
    private Button searchButton1;

    @FXML
    private Button searchButton2;

    @FXML
    private Button feelingLucky;

    @FXML
    private ListView searchResults;



    private List<Product> showResult(String token){
        ProductIndex p = ProductIndex.getInstance();
        List<String> mylist = new ArrayList<>();
        mylist.add("Herning");
        mylist.add("Køge");
        Product product = new Product(UUID.fromString("5cf4d1fd-2787-4b64-8ef9-0b6f131a3f23"),
                4.446,mylist, 20544709,
                1787.50, Instant.parse("2021-06-02T05:05:06.622164Z"), Instant.parse("2025-01-25T07:40:33.169509Z"),
                "Memory/SSD","Lenovo ThinkPad T410 35.8 cm (14.1\")",
                "Lenovo ThinkPad T410, 35.8 cm (14.1\")," +
                        " 1280 x 800 pixels Lenovo ThinkPad T410. " +
                        "Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels","12",52,
                1);
        List<Product> products = new ArrayList<>();
        List<String> searchTokens = new ArrayList<>();
        searchTokens.add(token);
        products.add(product);
        List<Product> indexedProducts = p.searchProducts(products, searchTokens);

        return indexedProducts;
    }

}
