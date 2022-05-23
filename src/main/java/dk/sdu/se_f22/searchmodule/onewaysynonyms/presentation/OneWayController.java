package dk.sdu.se_f22.searchmodule.onewaysynonyms.presentation;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.OneWayImage;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.ItemCatalog;
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
import java.util.LinkedList;
import java.util.ResourceBundle;

import static dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI.deleteItems;
import static dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI.readEntireDB;

public class OneWayController implements Initializable {

    @FXML
    public TextField CN_oldName, CN_newName, GI_Item, SearchBar,
            AI_ID, AI_Name, AI_SuperREF,
            MIB_Name, MIB_SuperID,
            DIInput;

    @FXML
    public ListView IDListView, ItemNameListView, SuperIDListView;
    @FXML
    public ImageView image;

    @FXML
    public Button generateImageButton, CN_enter, SearchButton, clearButton;
    @FXML
    public Label CN_status, ST_status, AI_status, MIB_Status, DIStatus;
    @FXML
    public TabPane TP_images;
    public ItemCatalog itemCatalog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TP_images.setDisable(true);
        TP_images.setVisible(false);
        itemCatalog = new ItemCatalog(readEntireDB());
    }

    public void GenerateImage(ActionEvent actionEvent) {
        this.itemCatalog = new ItemCatalog(readEntireDB());
        String input = GI_Item.getText();


        Item[] list = readEntireDB();

        OneWayImage owi = null;

        for (Item item : list) {
            if (isInt(input)){
                if (item.getId() == Integer.parseInt(input)) {
                owi = new OneWayImage(item);
                break;
                }
            }else {
                if (item.getName().equalsIgnoreCase(input)) {
                    owi = new OneWayImage(item);
                    break;
                }
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

    public void SearchTab(ActionEvent actionEvent) {
        boolean validSearch = false;
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
                if (item.getName().toLowerCase().equals(input) || item.getId() == i) {
                    IDListView.getItems().add(item.getId());
                    ItemNameListView.getItems().add(item.getName());
                    SuperIDListView.getItems().add(item.getSuperId());
                    ST_status.setText("Successful search");
                    validSearch = true;
                }
            }
            if (!validSearch) {
                ST_status.setText("Invalid search");
            }
        }
    }

    public void changeName(ActionEvent actionEvent) {
        int ID = validateInput(CN_oldName);
        if (ID > 0) {
            if (!isEmptyOrInt(CN_newName.getText())) {
                DatabaseAPI.updateName(ID, CN_newName.getText());
                CN_status.setText("Updated name to: " + CN_newName.getText());
                generateImageButton.fire();
            } else CN_status.setText("Invalid input for new name");
        } else if (ID == -1) CN_status.setText("Cannot change item name, use ID instead");
        else CN_status.setText("Invalid input");
    }

    public void addItem(ActionEvent actionEvent) {
        int IDSuperRef = validateInput(AI_SuperREF);
        if (IDSuperRef >= 0) {
            if (!isEmptyOrInt(AI_Name.getText())) {
                DatabaseAPI.addItem(AI_Name.getText(), IDSuperRef);
                AI_status.setText("Item added with name: " + AI_Name.getText());
                generateImageButton.fire();
            }
        } else if (IDSuperRef == -1) AI_status.setText("Cannot add item, use ID instead for super item reference");
        else AI_status.setText("Invalid Input");
    }

    public void moveItemButtonHandler(ActionEvent actionEvent) {
        int idItem, idSuperItem;
        idItem = validateInput(MIB_Name);
        idSuperItem = validateInput(MIB_SuperID);

        if (idItem > 0 && idSuperItem >= 0) {
            if (idItem == idSuperItem) {
                MIB_Status.setText("Cannot update super id to be the same as item id");
            }else {
                DatabaseAPI.updateSuperId(idItem, idSuperItem);
                MIB_Status.setText("Updated " + MIB_Name.getText() + " super item to: " + MIB_SuperID.getText());
                generateImageButton.fire();
            }
        } else if (idItem == -1) {
            MIB_Status.setText("Use ID for \"item to move\" instead");
        } else if (idSuperItem == -1) {
            MIB_Status.setText("Use ID for \"new super item\" instead");
        } else if (idItem == -2) {
            MIB_Status.setText("Invalid input for \"item to move\" field");
        } else MIB_Status.setText("Invalid input for \"new super item\" field");
    }

    public void deleteItem(ActionEvent actionEvent) {
        Item item;
        LinkedList<Item> subItems;
        int id = validateInput(DIInput);
        if (id > 0){
            item = itemCatalog.getItemInCatalog(id);
            if (item==null){
                DIStatus.setText("Invalid input");
            }else {
                subItems=item.getSubItems();
                for (Item subItem : subItems){
                    subItem.setSuperItem(item.getSuperItem());
                }
                deleteItems(item.getId());
                generateImageButton.fire();
            }
        }else if (id == -1) DIStatus.setText("Use ID instead");
        else DIStatus.setText("Invalid input");
    }

    //Method for validating input, essentially checks if the item reference be it ID or name is contained in the database.
    //It will return the ID if the item is in the database.
    //It will return -1 if there isn't any and 0 if there is more than 1
    private int validateInput(TextField textField) {
        int id = -1, amount = 0;

        if (textField.getText() != null) {
            if (isInt(textField.getText())) {
                id = Integer.parseInt(textField.getText());
                amount = itemCatalog.containsItem(Integer.parseInt(textField.getText()));
            } else {
                id = DatabaseAPI.searchBasedOnName(textField.getText());
                amount = itemCatalog.containsItem(textField.getText());
            }
        }

        if (amount == 1 & id >= 0 & !textField.getText().equalsIgnoreCase("root")) {
            return id;
        } else if (amount > 1) {
            return -1;
        } else {
            return -2;
        }
    }

    private boolean isInt(String text) {
        if (text != null) {
            try {
                int i = Integer.parseInt(text);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    private boolean isEmptyOrInt(String text) {
        if (text.equalsIgnoreCase("") || isInt(text)) {
            return true;
        } else return false;
    }

}
