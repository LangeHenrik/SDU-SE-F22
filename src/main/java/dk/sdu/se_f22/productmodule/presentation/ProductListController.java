package dk.sdu.se_f22.productmodule.presentation;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;

public class ProductListController {

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane main;

    @FXML
    private Button proceedBtn;

    @FXML
    private AnchorPane productDetails;

    @FXML
    private VBox productList;

    @FXML
    void initialize() {
        ArrayList<Product> products = App.productManager != null ? App.productManager.readAllProducts() : new ArrayList<>();

        for (Product p : products) {
            createProductListing(p);
        }
    }

    void createProductListing(Product product){
        AnchorPane listing = new AnchorPane();
        Label titel = new Label("product");
        listing.setOnMouseClicked((event) ->{
            updateProductDetailsView(product);
        });
        listing.getChildren().add(titel);
        AnchorPane.setLeftAnchor(titel,20.0);
        AnchorPane.setTopAnchor(titel, 20.0);
        productList.getChildren().add(listing);
    }

    void updateProductDetailsView(Product product){
        productDetails.getChildren().clear();
        VBox vBox = new VBox();
        vBox.setSpacing(20.0);

        for(int i = 0; i<ProductAttribute.values().length; i++){
            ProductAttribute attribute = ProductAttribute.values()[i];
            if(i%3 == 0){
                HBox hbox = new HBox();
                hbox.setSpacing(20.0);
                vBox.getChildren().add(hbox);
            }
            Node element = vBox.getChildren().get(i/3);
            if(element instanceof Pane){
                Pane parent = (Pane)element;
                parent.getChildren().add(new Label(attribute.alias + ": " + product.get(attribute)));
            }
        }
        productDetails.getChildren().add(vBox);
    }


    public void handleCancel(ActionEvent actionEvent) {
        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
            Scene scene = new Scene(fxmlLoader, 800,600);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleProceed(ActionEvent actionEvent) {
        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("productlisttokens.fxml"));
            Scene scene = new Scene(fxmlLoader, 800,600);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
