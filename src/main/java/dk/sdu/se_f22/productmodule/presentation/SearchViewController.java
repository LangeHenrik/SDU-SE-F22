package dk.sdu.se_f22.productmodule.presentation;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.List;

public class SearchViewController {


    @FXML
    private AnchorPane main;

    @FXML
    private VBox productView;

    @FXML
    private TextField searchField;


    public void handleSearch(ActionEvent actionEvent) {
        String searchFieldText = searchField.getText();
        SortingModuleImpl module = new SortingModuleImpl();
        module.setSearchString(searchFieldText);
        List<Product> searchReturn = module.search().getProducts();
        updateSearchResults(searchReturn);
    }

    public void updateSearchResults(List<Product> products){
        productView.getChildren().clear();

        for(int i = 0; i< products.size(); i++){
            Product product = products.get(i);
            if(i%3 == 0){
                HBox hbox = new HBox();
                hbox.setSpacing(20.0);
                productView.getChildren().add(hbox);
            }
            Node element = productView.getChildren().get(i/3);
            if(element instanceof Pane){
                Pane parent = (Pane)element;
                parent.getChildren().add(new Label(product.get(ProductAttribute.NAME)));
            }
        }

        if (products.size() == 0) {
            productView.getChildren().add(new Label("No results found!"));
        }
    }
}
