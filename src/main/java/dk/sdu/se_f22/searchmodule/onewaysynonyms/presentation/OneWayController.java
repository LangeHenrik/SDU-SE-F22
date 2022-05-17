package dk.sdu.se_f22.searchmodule.onewaysynonyms.presentation;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.OneWayImage;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.ItemCatalog;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.OneWayImplementation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import static dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI.readEntireDB;

public class OneWayController implements Initializable {

    @FXML
    public TextField insertSuperIDAddItemTextfield, insertNameAddItemTextfield,
            CN_oldName, CN_newName, GI_Item, SearchBar,
            AI_ID, AI_Name, AI_SuperREF,
            MIB_Name, MIB_SuperID;

    @FXML
    public ListView IDListView, ItemNameListView, SuperIDListView;
    @FXML
    public ImageView image;

    @FXML
    public Button CN_enter, SearchButton, clearButton;
    @FXML
    public Label CN_status, ST_status, AI_status, MIB_Status;

    @FXML
    public TabPane TP_images;
    public ToggleGroup AddItemRadioGroup, MIBRadioGroup1, MIBRadioGroup2;

    public ItemCatalog itemCatalog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TP_images.setDisable(true);
        TP_images.setVisible(false);

        itemCatalog = new ItemCatalog(readEntireDB());

    }


    public void addItemButtonHandler(ActionEvent actionEvent) {/*
        if (insertSuperIDAddItemTextfield.getText()=="") {
            try {
                DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                int superId = Integer.parseInt(insertSuperIDAddItemTextfield.getText());
                DatabaseAPI.addItem(insertNameAddItemTextfield.getText(), superId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    */
    }

    public void SearchTab(ActionEvent actionEvent) {
        OneWayImplementation owi = new OneWayImplementation();
        owi.showCatalog();
        if (actionEvent.getSource().equals(clearButton)) {
            IDListView.getItems().clear();
            ItemNameListView.getItems().clear();
            SuperIDListView.getItems().clear();
        } else {
            Item[] items = readEntireDB();
            String input = SearchBar.getText().toLowerCase();
            int i = -1;
            if (isInt(input)) {
                i = Integer.parseInt(input);
            }
            for (Item item : items) {
                System.out.println(item);
                if (item.getName().toLowerCase().equals(input) || item.getId() == i) {
                    IDListView.getItems().add(item.getId());
                    ItemNameListView.getItems().add(item.getName());
                    SuperIDListView.getItems().add(item.getSuperId());
                    ST_status.setText("Successful search");

                } else ST_status.setText("Invalid search");
            }
        }
    }

    public void addItem(ActionEvent actionEvent) {
        if (AddItemRadioGroup.getSelectedToggle().equals(AddItemRadioGroup.getToggles().get(0))) {
            int amount = itemCatalog.containsItem(AI_SuperREF.getText());

            if (amount == 1) {
                DatabaseAPI.addItem(AI_Name.getText(), AI_SuperREF.getText());
                AI_status.setText("Item Added");
            } else if (amount > 1) AI_status.setText("Cannot add item, use ID instead");
            else AI_status.setText("Invalid input");
        } else {
            if (AI_SuperREF.getText() != null) {
                if (isInt(AI_SuperREF.getText()) & Integer.parseInt(AI_SuperREF.getText()) > 0) {
                    DatabaseAPI.addItem(AI_Name.getText(), Integer.parseInt(AI_SuperREF.getText()));
                    AI_status.setText("Item Added");
                } else AI_status.setText("Invalid Input");
            }
        }
    }

    private boolean isInt(String text) {
        try {
            int i = Integer.parseInt(text);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void GenerateImage(ActionEvent actionEvent) {
        this.itemCatalog = new ItemCatalog(readEntireDB());
        String name = GI_Item.getText();

        Item[] list = readEntireDB();

        OneWayImage owi = null;

        for (Item item : list) {
            if (item.getName().equalsIgnoreCase(name)) {
                owi = new OneWayImage(item);
            }
        }
        if (owi == null) {
            owi = new OneWayImage(list[0]);
        }

        image.setImage(convertToFxImage(owi.getImage()));
        TP_images.setDisable(false);
        TP_images.setVisible(true);
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public void changeName(ActionEvent actionEvent) {
        int id;
        id = DatabaseAPI.searchBasedOnName(CN_oldName.getText());
        if (id == -1) {
            CN_status.setText("Invalid name");
            return;
        }
        DatabaseAPI.updateName(id, CN_newName.getText());
        CN_status.setText("Updated Name to: " + CN_newName.getText());
    }


    public void moveItemButtonHandler(ActionEvent actionEvent) {
        if (MIBRadioGroup1.getSelectedToggle().equals(MIBRadioGroup1.getToggles().get(0))) {
            int amount = itemCatalog.containsItem(MIB_Name.getText());
            if (amount == 1) {

            } else if (amount > 1) {
                MIB_Status.setText("Cannot add item, use ID instead");
            } else AI_status.setText("Invalid input");
        } else {
            if (MIB_Name.getText() != null) {
                if (isInt(MIB_Name.getText()) & Integer.parseInt(MIB_Name.getText()) > 0) {

                }
            } else AI_status.setText("Invalid input");
        }
    }
    private boolean getSuperItem(ToggleGroup toggleGroup, TextField textField){
        if (toggleGroup.getSelectedToggle().equals(toggleGroup.getToggles().get(0))){
            int amount = itemCatalog.containsItem(textField.getText());
            if (amount == 1){
                return true;
            }
        }
        return false;
    }
}
