package dk.sdu.se_f22.brandmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import java.time.LocalDateTime;
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

	private void useDemoData() {
		brandsListView.setItems(observableList(List.of(
			new Brand(
				0,
				"Dell",
				"Dell is an American company that develops, sells, repairs, and supports computers and related products and services, and is owned by its parent company of Dell Technologies. Founded in 1984 by Michael Dell, the company is one of the largest technology corporations in the world, employing more than 165,000 people in the United States (US) and around the world. Dell sells personal computers (PCs), servers, data storage devices, network switches, software, computer peripherals, HDTVs, cameras, printers, and electronics built by other manufacturers. The company is well known for its innovations in supply chain management and electronic commerce, particularly its direct-sales model and its \"build-to-order\" or \"configure to order\" approach to manufacturing—delivering individual PCs configured to customer specifications. Dell was a pure hardware vendor for much of its existence, but with the acquisition in 2009 of Perot Systems, Dell entered the market for IT services. The company has since made additional acquisitions in storage and networking systems, with the aim of expanding their portfolio from offering computers only to delivering complete solutions for enterprise customers.",
				"February 1, 1984",
				"Round Rock, Texas, US",
				List.of(
					"Personal computers",
					"Servers",
					"Peripherals",
					"Smartphones",
					"Televisions"
				)),
			new Brand(
				1,
				"Samsung",
				"The Samsung Group (or simply Samsung, stylized in logo as SΛMSUNG) is a South Korean multinational manufacturing conglomerate headquartered in Samsung Town, Seoul, South Korea. It comprises numerous affiliated businesses, most of them united under the Samsung brand, and is the largest South Korean chaebol (business conglomerate). As of 2020, Samsung has the 8th highest global brand value.",
				"1 March 1938",
				"Seocho District, Seoul, South Korea",
				List.of(
					"Clothing",
					"automotive",
					"chemicals",
					"consumer electronics",
					"electronic components",
					"medical equipment",
					"semiconductors",
					"solid state drives",
					"DRAM",
					"flash memory",
					"ships",
					"telecommunications equipment",
					"home appliance"
				)
			),
			new Brand(
				2,
				"Intel",
				"Intel Corporation, stylized as intel, is an American multinational corporation and technology company headquartered in Santa Clara, California. It is the world's largest semiconductor chip manufacturer by revenue, and is the developer of the x86 series of microprocessors, the processors found in most personal computers (PCs). Incorporated in Delaware, Intel ranked No. 45 in the 2020 Fortune 500 list of the largest United States corporations by total revenue for nearly a decade, from 2007 to 2016 fiscal years. Intel supplies microprocessors for computer system manufacturers such as Acer, Lenovo, HP, and Dell. Intel also manufactures motherboard chipsets, network interface controllers and integrated circuits, flash memory, graphics chips, embedded processors and other devices related to communications and computing.",
				"July 18, 1968",
				"San Francisco California, U.S.",
				List.of(
					"Central processing units",
					"Microprocessors",
					"Integrated graphics processing units (iGPU)",
					"Systems-on-chip (SoCs)",
					"Motherboard chipsets",
					"Network interface controllers",
					"Modems",
					"Mobile phones",
					"Solid state drives",
					"Wi-Fi and Bluetooth Chipsets",
					"Flash memory",
					"Vehicle automation sensors"
				)
			)
		)));

		ignoreField.setText("[\\.,]");
		delimiterField.setText(" ");
		queryTokensListView.setItems(observableList(List.of(
			"Computers",
			"Smartphones"
		)));
	}

	@FXML
	public void onBrandClicked() {
		selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
		nameField.setText(selectedBrand.getName());
		descriptionField.setText(selectedBrand.getDescription());
		foundedField.setText(selectedBrand.getFounded());
		headquartersField.setText(selectedBrand.getHeadquarters());
		productsListView.setItems(observableList(selectedBrand.getProducts()));
		brandClickedLast = LocalDateTime.now();
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
		if (brandClickedLast.plusNanos(10000000).isBefore(LocalDateTime.now())) return;
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
