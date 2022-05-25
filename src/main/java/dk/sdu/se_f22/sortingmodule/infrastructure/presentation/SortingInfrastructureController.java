package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModule;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.scoring.ScoreSortType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SortingInfrastructureController {
    public TextField searchField;
    public Button searchBtn;
    public ChoiceBox scoringList;

    public TextArea productList;
    public TextArea brandList;
    public TextArea contentList;

    public ScrollPane categoryList;
    public ScrollPane rangeList;

    public CheckBox mockProduct;
    public CheckBox mockBrand;
    public CheckBox mockContent;

    public TextField page;
    public TextField pageSize;

    public TableView queryTable;
    public TableColumn queryTableId;
    public TableColumn queryTableText;
    public TableColumn queryTablePage;
    public TableColumn queryTablePageSize;
    public TableColumn queryTableScoring;
    public TableColumn queryTableTime;
    public TextArea queryInformation;
    public ObservableList<QueryLog> data = FXCollections.observableArrayList();

    private SortingModule sortingModule;

    private HashMap<CheckBox, Category> categorySelectors;
    private ArrayList<RangeSelector> rangeSelectors;

    @FXML
    public void initialize() {
        sortingModule = new SortingModuleImpl();

        // Set score types
        for (ScoreSortType type : ScoreSortType.values()) {
            scoringList.getItems().add(type.toString());
        }
        scoringList.setValue(ScoreSortType.values()[0]);

        // Set category filtering
        categorySelectors = new HashMap<>();
        VBox categoryListContainer = new VBox();
        categoryListContainer.setSpacing(10);
        categoryListContainer.setPadding(new Insets(10));
        for (Object category : sortingModule.getAllCategories()) {
            if (category instanceof Category) {
                Category currentCategory = (Category) category;
                CheckBox checkBox = new CheckBox(currentCategory.getName());
                categorySelectors.put(checkBox, currentCategory);
                categoryListContainer.getChildren().add(checkBox);
                checkBox.setIndeterminate(false);
            }
        }
        categoryList.setContent(categoryListContainer);

        // Set range filtering
        rangeSelectors = new ArrayList<>();
        VBox RangeListContainer = new VBox();
        RangeListContainer.setSpacing(10);
        RangeListContainer.setPadding(new Insets(10));
        for (Object range : sortingModule.getAvailableRangeFilters()) {
            if (range instanceof RangeFilter) {
                RangeFilter currentRange = (RangeFilter) range;

                // Label
                CheckBox rangeLabel = new CheckBox(currentRange.getName());
                RangeListContainer.getChildren().add(rangeLabel);
                HBox inputBox = new HBox();
                inputBox.setPadding(new Insets(0, 0, 0, 10));
                Label startLabel = new Label("Start");
                inputBox.getChildren().add(startLabel);
                Label arrowLabel = new Label(" => ");

                switch (currentRange.getType()) {
                    default:
                    case DOUBLE:
                    case LONG:

                        // Inputs
                        TextField startText = new TextField();
                        inputBox.getChildren().add(startText);
                        inputBox.getChildren().add(arrowLabel);
                        TextField endText = new TextField();
                        inputBox.getChildren().add(endText);

                        rangeSelectors.add(new RangeSelector(currentRange, rangeLabel, startText, endText));
                        break;
                    case TIME:
                        DatePicker startDate = new DatePicker();
                        inputBox.getChildren().add(startDate);
                        inputBox.getChildren().add(arrowLabel);
                        DatePicker endDate = new DatePicker();
                        inputBox.getChildren().add(endDate);

                        rangeSelectors.add(new RangeSelector(currentRange, rangeLabel, startDate, endDate));
                        break;
                }
                RangeListContainer.getChildren().add(inputBox);
            }
        }
        rangeList.setContent(RangeListContainer);

        // Page
        page.setText("" + sortingModule.getQuery().getPagination()[0]);
        pageSize.setText("" + sortingModule.getQuery().getPagination()[1]);

        // Logging table
        queryTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        queryTableText.setCellValueFactory(new PropertyValueFactory<QueryLog, String>("text"));
        queryTablePage.setCellValueFactory(new PropertyValueFactory<QueryLog, Integer>("page"));
        queryTablePageSize.setCellValueFactory(new PropertyValueFactory<QueryLog, Integer>("pageSize"));
        queryTableScoring.setCellValueFactory(new PropertyValueFactory<QueryLog, String>("scoring"));
        queryTableTime.setCellValueFactory(new PropertyValueFactory<QueryLog, String>("time"));
        updateLogTable();
        queryTable.setItems(data);

        queryTable.setOnMouseClicked(event -> {
            // Make sure the user clicked on a populated item
            if (queryTable.getSelectionModel().getSelectedItem() != null) {
                try {
                    QueryLog currentLog = (QueryLog) queryTable.getSelectionModel().getSelectedItem();

                    queryInformation.setText("");

                    queryInformation.appendText("Id: " + currentLog.id + "\n");
                    queryInformation.appendText("Text: " + currentLog.text + "\n");
                    queryInformation.appendText("Page: " + currentLog.page + "\n");
                    queryInformation.appendText("Page size: " + currentLog.pageSize + "\n");
                    queryInformation.appendText("Scoring: " + currentLog.scoring + "\n");
                    queryInformation.appendText("Time: " + currentLog.time + "\n");
                    queryInformation.appendText("\n");

                    try (Connection connection = DBConnection.getPooledConnection()) {
                        queryInformation.appendText("Category filters:\n");
                        PreparedStatement stmt = connection
                                .prepareStatement("SELECT * FROM sorting_query_categories WHERE query_id = ?");
                        stmt.setInt(1, currentLog.id);
                        ResultSet data = stmt.executeQuery();
                        HashMap<Integer, Category> categoryMap = new HashMap<>();
                        for (Object category : sortingModule.getAllCategories()) {
                            if (category instanceof Category) {
                                categoryMap.put(((Category) category).getId(), (Category) category);
                            }
                        }
                        while (data.next()) {
                            queryInformation.appendText("(" + data.getString("category_id") + ") - "
                                    + categoryMap.get(data.getInt("category_id")).getName() + "\n");
                        }
                        data.close();
                        stmt.close();

                        queryInformation.appendText("\n");
                        queryInformation.appendText("Range filters:\n");
                        stmt = connection
                                .prepareStatement("SELECT * FROM sorting_query_ranges WHERE query_id = ?");
                        stmt.setInt(1, currentLog.id);
                        data = stmt.executeQuery();
                        HashMap<Integer, RangeFilter> rangeMap = new HashMap<>();
                        for (Object range : sortingModule.getAvailableRangeFilters()) {
                            if (range instanceof RangeFilter) {
                                rangeMap.put(((RangeFilter) range).getId(), (RangeFilter) range);
                            }
                        }
                        while (data.next()) {
                            RangeFilter currentRange = rangeMap.get(data.getInt("range_id"));
                            queryInformation.appendText("(" + currentRange.getId() + ") - "
                                    + currentRange.getName() + " | " + data.getString("start_value") + " => "
                                    + data.getString("end_value") + "\n");
                        }
                        data.close();
                        stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<Integer> getSlectedCategories() {
        ArrayList<Integer> list = new ArrayList<>();
        categorySelectors.forEach((checkbox, category) -> {
            if (checkbox.isSelected()) {
                list.add(category.getId());
            }
        });
        return list;
    }

    private void addRangeFilters() {
        sortingModule.clearRange();
        rangeSelectors.forEach((rangeSelector) -> {
            if (rangeSelector.checkBox.isSelected()) {
                try {
                    switch (rangeSelector.range.getType()) {
                        default:
                        case DOUBLE:
                            double startRangeDouble = Double.parseDouble(rangeSelector.startNumber.getText());
                            double endRangeDouble = Double.parseDouble(rangeSelector.endNumber.getText());
                            sortingModule.addRange(rangeSelector.range.getId(), startRangeDouble, endRangeDouble);
                            break;
                        case LONG:
                            long startRangeLong = Long.parseLong(rangeSelector.startNumber.getText());
                            long endRangeLong = Long.parseLong(rangeSelector.endNumber.getText());
                            sortingModule.addRange(rangeSelector.range.getId(), startRangeLong, endRangeLong);
                            break;
                        case TIME:
                            Instant startRangeTime = Instant
                                    .ofEpochSecond(rangeSelector.startTime.getValue().toEpochDay());
                            Instant endRangeTime = Instant.ofEpochSecond(rangeSelector.endTime.getValue().toEpochDay());
                            sortingModule.addRange(rangeSelector.range.getId(), startRangeTime, endRangeTime);
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(Color.RED + "Error parsing range values!" + Color.RESET);
                }
            }
        });
    }

    public void search(ActionEvent actionEvent) {
        sortingModule.setScoring(ScoreSortType.valueOf(scoringList.getValue().toString()));
        sortingModule.setSearchString(searchField.getText());

        sortingModule.useMockData(mockBrand.isSelected(), mockContent.isSelected(), mockProduct.isSelected());

        sortingModule.setCategory(getSlectedCategories());

        sortingModule.setPagination(
                Integer.parseInt(page.getText()),
                Integer.parseInt(pageSize.getText()));

        addRangeFilters();

        SearchHits searchHits = sortingModule.search();

        productList.setText("");
        for (Product product : searchHits.getProducts()) {
            productList.appendText(product.getName() + "\n");
        }
        brandList.setText("");
        for (Brand brand : searchHits.getBrands()) {
            brandList.appendText(brand.getName() + "\n");
        }
        contentList.setText("");
        for (Content content : searchHits.getContents()) {
            contentList.appendText(content.getTitle() + "\n");
        }

        updateLogTable();
        queryTable.setItems(data);
    }

    public void ScoringListAction(InputMethodEvent inputMethodEvent) {
    }

    public void updateLogTable() {
        this.data = FXCollections.observableArrayList();
        try (Connection connection = DBConnection.getPooledConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet data = stmt.executeQuery("SELECT * FROM sorting_queries ORDER BY id");
            while (data.next()) {
                this.data.add(new QueryLog(data.getInt("id"), data.getString("text"), data.getInt("page"),
                        data.getInt("page_size"), ScoreSortType.valueOf(data.getString("scoring")),
                        data.getString("date_stamp")));
            }
            data.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class RangeSelector {
        public RangeFilter range;
        public CheckBox checkBox;
        public DatePicker startTime;
        public DatePicker endTime;
        public TextField startNumber;
        public TextField endNumber;

        public RangeSelector(RangeFilter range, CheckBox checkBox, DatePicker start, DatePicker end) {
            this.range = range;
            this.checkBox = checkBox;
            this.startTime = start;
            this.endTime = end;
        }

        public RangeSelector(RangeFilter range, CheckBox checkBox, TextField start, TextField end) {
            this.range = range;
            this.checkBox = checkBox;
            this.startNumber = start;
            this.endNumber = end;
        }
    }

    public class QueryLog {
        public int id;
        public String text;
        public int page;
        public int pageSize;
        public ScoreSortType scoring;
        public String time;

        public QueryLog(int id, String text, int page, int pageSize, ScoreSortType scoring, String time) {
            this.id = id;
            this.text = text;
            this.page = page;
            this.pageSize = pageSize;
            this.scoring = scoring;
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public int getPage() {
            return page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public ScoreSortType getScoring() {
            return scoring;
        }

        public String getTime() {
            return time;
        }
    }
}
