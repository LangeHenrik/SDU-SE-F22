package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import javax.swing.*;
import java.awt.*;

public class OneWayFrame extends JFrame {

    private OneWayPanel panel;

    public OneWayFrame(Item item){
        this.panel = new OneWayPanel(item);
        this.add(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
