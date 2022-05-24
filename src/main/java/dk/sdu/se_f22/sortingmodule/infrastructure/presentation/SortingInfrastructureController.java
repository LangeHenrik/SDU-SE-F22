package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModule;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.scoring.ScoreSortType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    private SortingModule sortingModule;
    
    private HashMap<CheckBox, Category> categorySelectors;
    private ArrayList<RangeSelector> rangeSelectors;

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
                inputBox.setPadding(new Insets(0,0,0,10));
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
        page.setText(""+sortingModule.getQuery().getPagination()[0]);
        pageSize.setText(""+sortingModule.getQuery().getPagination()[1]);
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
                            Instant startRangeTime = Instant.ofEpochSecond(rangeSelector.startTime.getValue().toEpochDay());
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
            Integer.parseInt(pageSize.getText())
        );

        addRangeFilters();

        SearchHits searchHits = sortingModule.search();

        productList.setText("");
        for (Product product : searchHits.getProducts()) {
            productList.appendText(product.getName()+"\n");
        }
        brandList.setText("");
        for (Brand brand : searchHits.getBrands()) {
            brandList.appendText(brand.getName()+"\n");
        }
        contentList.setText("");
        for (Content content : searchHits.getContents()) {
            contentList.appendText(content.getTitle()+"\n");
        }

    }
    public void ScoringListAction(InputMethodEvent inputMethodEvent) {
    }
}
