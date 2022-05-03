package dk.sdu.se_f22.brandmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Bim2Controller {
	BrandInfrastructureInterface brandInfrastructure;

	@FXML
	TextField nameField, descriptionField, foundedField, headquartersField;
	@FXML
	ListView<String> productsListView;
	@FXML
	ListView<Brand> brandsListView;

	@FXML
	TextField delimiterField, ignoreField;

	@FXML
	ListView<String> queryTokensListView;

	Brand selectedBrand;


	@FXML
	public void initialize() {
		brandInfrastructure = new BrandInfrastructure();

		productsListView.setEditable(true);
		productsListView.setCellFactory(TextFieldListCell.forListView());
		queryTokensListView.setEditable(true);
		queryTokensListView.setCellFactory(TextFieldListCell.forListView());
	}

	@FXML
	public void onBrandClicked() {
		selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void addProduct() {
		productsListView.getItems().add("New Product");
	}

	@FXML
	public void removeProduct() {
		String selectedItem = productsListView.getSelectionModel().getSelectedItem();
		if (selectedItem == null) return;
		productsListView.getItems().remove(selectedItem);
	}

	public void updateBrand() {
		if (selectedBrand == null) return;
		selectedBrand.setName(nameField.getText());
		selectedBrand.setDescription(descriptionField.getText());
		selectedBrand.setFounded(foundedField.getText());
		selectedBrand.setHeadquarters(headquartersField.getText());
		selectedBrand.setProducts(productsListView.getItems());
	}

	@FXML
	public void addBrand() {
		String name = nameField.getText();
		String description = descriptionField.getText();
		String founded = foundedField.getText();
		String headquarters = headquartersField.getText();
		List<String> products = productsListView.getItems();
		Brand toAdd = new Brand(name, description, founded, headquarters, products);
		brandsListView.getItems().add(toAdd);
	}

	@FXML
	public void removeBrand() {
		Brand selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
		if (selectedBrand == null) return;
		brandsListView.getItems().remove(selectedBrand);
	}

	@FXML
	public void indexBrands() {
		brandInfrastructure.indexBrands(brandsListView.getItems());
	}

	@FXML
	public void setTokenizationParameters() {
		String delimiter = delimiterField.getText();
		String ignore = ignoreField.getText();
		brandInfrastructure.setTokenizationParameters(delimiter, ignore);
	}

	@FXML
	public void addToken() {
		queryTokensListView.getItems().add("New Token");
	}

	@FXML
	public void removeToken() {
		String selectedItem = queryTokensListView.getSelectionModel().getSelectedItem();
		if (selectedItem == null) return;
		queryTokensListView.getItems().remove(selectedItem);
	}

	@FXML
	public void queryIndex() {
		List<String> tokens = queryTokensListView.getItems();
		brandInfrastructure.queryIndex(tokens);
	}
}
