package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Label;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OneWayController implements Initializable {

    @FXML
    public TextField insertSuperIDAddItemTextfield,insertNameAddItemTextfield, CN_oldName, CN_newName;

    @FXML
    public ImageView image;

    @FXML
    public Button CN_enter;
    @FXML
    public Label CN_status;

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


    public void GenerateImage(ActionEvent actionEvent) {

        ItemCatalog test = new ItemCatalog(DatabaseAPI.readEntireDB());
        OneWayImage owi = new OneWayImage(test.getRoot());


        image.setImage(convertToFxImage(owi.getImage()));
        image.fitHeightProperty();
        image.fitWidthProperty();
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
        if (insertNameAddItemTextfield.getText() == null ) {
            try {
                DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                int superId = Integer.parseInt(insertNameAddItemTextfield.getText());
                DatabaseAPI.addItem(insertNameAddItemTextfield.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeName(ActionEvent actionEvent) {
        int id;
        id = DatabaseAPI.searchBasedOnName(CN_oldName.getText());
        if(id == -1){
            CN_status.setText("Invalid name");
            return;
        }
        try {
            DatabaseAPI.updateName(id,CN_newName.getText());
        } catch (SQLException e) {
            CN_status.setText("Something went Wrong, try again");
        }
    }


}
