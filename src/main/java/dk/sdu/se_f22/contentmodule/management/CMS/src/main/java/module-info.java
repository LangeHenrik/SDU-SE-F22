module semesterproject.cms.cms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jsoup;
    requires java.sql;
    requires postgresql;
    requires org.junit.jupiter.api;

    opens UI to javafx.fxml;
    exports UI;
}