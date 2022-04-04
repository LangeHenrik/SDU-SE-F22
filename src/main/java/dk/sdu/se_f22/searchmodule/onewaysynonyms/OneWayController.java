package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class OneWayController {

    @FXML
    public TextField insertSuperIDAddItemTextfield,insertNameAddItemTextfield;

    @FXML
    public ImageView image;

    @FXML
    public void addItemButtonHandler(ActionEvent actionEvent) {
        if (insertSuperIDAddItemTextfield.getText() == null) {
            try {
                DatabaseAPI.addItem(String.valueOf(insertNameAddItemTextfield));
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

    }


    public void GenerateImage(ActionEvent actionEvent) {
        Item i1 = new Item("køretøjer");
        Item i2 = new Item("motordrevet",i1);
        Item i3 = new Item("menneskedrevet",i1);
        Item i4 = new Item("racerbil",i2);
        Item i5 = new Item("personbil",i2);
        Item i6 = new Item("lastbil",i2);
        Item i7 = new Item("superbil",i5);
        Item i8 = new Item("5 dørs",i5);
        Item i9 = new Item("3 dørs",i5);
        Item i10 = new Item("børnecontainer",i8);
        Item i11 = new Item("lukus",i8);
        Item i12 = new Item("cykel",i3);
        Item i13 = new Item("løbehjul",i3);
        Item i14 = new Item("skateboard",i3);

        Item[] list = new Item[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14};
        ItemCatalog test = new ItemCatalog(list);
        OneWayImage owi = new OneWayImage(i1);

        image.setImage(convertToFxImage(owi.getImage()));

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
}
