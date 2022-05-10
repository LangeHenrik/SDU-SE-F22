package dk.sdu.se_f22.searchmodule.onewaysynonyms;

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

public class OneWayController implements Initializable {

    @FXML
    public TextField insertSuperIDAddItemTextfield, insertNameAddItemTextfield,
            CN_oldName, CN_newName, GI_Item, SearchBar,
            AI_ID, AI_Name, AI_SuperID;

    @FXML
    public ListView IDListView, ItemNameListView, SuperIDListView;
    @FXML
    public ImageView image;

    @FXML
    public Button CN_enter, SearchButton, clearButton;
    @FXML
    public Label CN_status, ST_status, AI_status;

    @FXML
    public TabPane TP_images;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TP_images.setDisable(true);
        TP_images.setVisible(false);
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
        if (actionEvent.getSource().equals(clearButton)) {
            IDListView.getItems().clear();
            ItemNameListView.getItems().clear();
            SuperIDListView.getItems().clear();
        } else {
            Item[] items = DatabaseAPI.readEntireDB();
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
                    break;
                } else ST_status.setText("Invalid search");
            }
        }
    }

    public void addItem(ActionEvent actionEvent){
        if (isInt(AI_SuperID.getText()) & Integer.parseInt(AI_SuperID.getText())>0){
            DatabaseAPI.addItem(AI_Name.getText(),Integer.parseInt(AI_SuperID.getText()));
            AI_status.setText("Item Added");
        }else AI_status.setText("Invalid Input");
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
        String name = GI_Item.getText();

        Item[] list = DatabaseAPI.readEntireDB();

//        Item i1 = new Item("køretøjer");
//        Item i2 = new Item("motordrexvet", i1);
//        Item i3 = new Item("menneskedrevet", i1);
//        Item i4 = new Item("racerbil", i2);
//        Item i5 = new Item("personbil", i2);
//        Item i6 = new Item("lastbil", i2);
//        Item i7 = new Item("superbil", i5);
//        Item i8 = new Item("5 dørs", i5);
//        Item i9 = new Item("3 dørs", i5);
//        Item i10 = new Item("børnecontainer", i8);
//        Item i11 = new Item("lukus", i8);
//        Item i12 = new Item("cykel", i3);
//        Item i13 = new Item("løbehjul", i3);
//        Item i14 = new Item("skateboard", i3);
//
//        Item[] list = new Item[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14};

        ItemCatalog test = new ItemCatalog(list);
        OneWayImage owi = null;

        for (Item item : list) {
            if (item.getName().equalsIgnoreCase(name)) {
                owi = new OneWayImage(item);
            }
        }
        if (owi == null) {
            owi = new OneWayImage(list[12]);
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

    @FXML
    public void insertItemReadItemTextField(ActionEvent actionEvent) {
        if (insertNameAddItemTextfield.getText() == null) {
            DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield));
        } else {
            int superId = Integer.parseInt(insertNameAddItemTextfield.getText());
            DatabaseAPI.addItem(insertNameAddItemTextfield.getText());
        }
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


}
