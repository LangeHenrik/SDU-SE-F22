module dk.sdu.se_f22 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.dlsc.formsfx;
    requires commons.dbcp2;
    requires java.management;
    requires java.sql;
    requires json.simple;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;
    requires org.controlsfx.controls;
    requires org.postgresql.jdbc;
    requires validatorfx;
    requires com.google.gson;

    opens dk.sdu.se_f22 to javafx.fxml;
    exports dk.sdu.se_f22;
}
