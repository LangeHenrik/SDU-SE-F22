package dk.sdu.se_f22.productmodule.presentation;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfIndex;
import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListTokensController {
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

    private List<Product> allProducts;

    private List<Product> indexedProducts = new ArrayList<>();

    @FXML
    void initialize() {
        this.allProducts = App.productManager != null ? App.productManager.getAllProducts() : new ArrayList<>();
        Product product = new Product();
        product.set(ProductAttribute.NAME, "Wubba");
        product.set(ProductAttribute.DESCRIPTION, "tre tre tre tre");
        this.allProducts.add(product);
        indexedProducts.addAll(allProducts);
        for (Product p : this.allProducts) {
            createProductListing(p);
        }
        updateProductDetailsView(allProducts.get(0));
    }

    void createProductListing(Product product){
        Label titel = new Label("product");
        HBox listing = new HBox();
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    indexedProducts.add(product);
                }
                else {
                    indexedProducts.remove(product);
                }
            }
        });
        listing.setOnMouseClicked((event) ->{
            updateProductDetailsView(product);
        });
        listing.getChildren().addAll(titel,checkBox);
        AnchorPane.setLeftAnchor(titel,20.0);
        AnchorPane.setTopAnchor(titel, 20.0);
        productList.getChildren().add(listing);
    }

    void updateProductDetailsView(Product product){
        ProductInfIndex productInfIndex = ProductIndexInfrastructure.getInstance().getProductIndex();
        productDetails.getChildren().clear();
        VBox vBox = new VBox();
        vBox.setSpacing(20.0);

        Label initialToken = new Label();
        List<String> tokens = productInfIndex.getInitialProductTokens(product);
        initialToken.setText(
                String.valueOf(tokens)
        );

        Label filteredToken = new Label();
        filteredToken.setText(
                String.valueOf(productInfIndex.getFilteredProductTokens(tokens))
        );

        vBox.getChildren().add(initialToken);
        vBox.getChildren().add(filteredToken);

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
        ProductInfIndex productInfIndex = ProductIndexInfrastructure.getInstance().getProductIndex();
        productInfIndex.indexProducts(indexedProducts);

        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
            Scene scene = new Scene(fxmlLoader, 800,600);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
