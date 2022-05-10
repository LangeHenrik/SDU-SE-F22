package dk.sdu.se_f22.brandmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import org.w3c.dom.events.MouseEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableList;

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
	@FXML
	ListView<Brand> queryResultsListView;

	private Brand selectedBrand;

	private LocalDateTime brandClickedLast;

	@FXML
	public void initialize() {
		brandInfrastructure = new BrandInfrastructure();

		productsListView.setEditable(true);
		productsListView.setCellFactory(TextFieldListCell.forListView());

		queryTokensListView.setEditable(true);
		queryTokensListView.setCellFactory(TextFieldListCell.forListView());

		ChangeListener<String> listener = (obs, oldText, newText) -> updateBrand();

		nameField.textProperty().addListener(listener);
		descriptionField.textProperty().addListener(listener);
		foundedField.textProperty().addListener(listener);
		headquartersField.textProperty().addListener(listener);
	}

	@FXML
	public void onBrandClicked() {
		selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
		nameField.setText(selectedBrand.getName());
		descriptionField.setText(selectedBrand.getDescription());
		foundedField.setText(selectedBrand.getFounded());
		headquartersField.setText(selectedBrand.getHeadquarters());
		productsListView.setItems(observableList(selectedBrand.getProducts()));
		brandClickedLast = LocalDateTime.now().plusSeconds(1);
	}

	@FXML
	public void addProduct() {
		String toAdd = "New Product";
		productsListView.getItems().add(toAdd);
		productsListView.getSelectionModel().select(toAdd);
	}

	@FXML
	public void removeProduct() {
		String selectedItem = productsListView.getSelectionModel().getSelectedItem();
		if (selectedItem == null) return;
		productsListView.getItems().remove(selectedItem);
	}

	public void updateBrand() {
		if (!brandClickedLast.isBefore(LocalDateTime.now())) return;
		if (selectedBrand == null) return;
		selectedBrand.setName(nameField.getText());
		selectedBrand.setDescription(descriptionField.getText());
		selectedBrand.setFounded(foundedField.getText());
		selectedBrand.setHeadquarters(headquartersField.getText());
		selectedBrand.setProducts(productsListView.getItems());
		brandsListView.refresh();
	}

	@FXML
	public void addBrand() {
		Brand toAdd = new Brand("", "", "", "");
		brandsListView.getItems().add(toAdd);
		brandsListView.getSelectionModel().select(toAdd);
		selectedBrand = toAdd;
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
		List<Brand> results = brandInfrastructure.queryIndex(tokens);
		queryResultsListView.setItems(observableList(results));
	}
}
