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

    exports dk.sdu.se_f22;
    opens dk.sdu.se_f22 to javafx.fxml;

    opens dk.sdu.se_f22.brandmodule.index            to javafx.fxml;
    opens dk.sdu.se_f22.brandmodule.infrastructure   to javafx.fxml;
    opens dk.sdu.se_f22.brandmodule.management       to javafx.fxml;
    opens dk.sdu.se_f22.brandmodule.stemming         to javafx.fxml;
    opens dk.sdu.se_f22.contentmodule.index          to javafx.fxml;
    opens dk.sdu.se_f22.contentmodule.infrastructure to javafx.fxml;
    opens dk.sdu.se_f22.contentmodule.management     to javafx.fxml;
    opens dk.sdu.se_f22.contentmodule.stopwords      to javafx.fxml;
    opens dk.sdu.se_f22.productmodule.index          to javafx.fxml;
    opens dk.sdu.se_f22.productmodule.infrastructure to javafx.fxml;
    opens dk.sdu.se_f22.productmodule.irregularwords to javafx.fxml;
    opens dk.sdu.se_f22.productmodule.management     to javafx.fxml;
    opens dk.sdu.se_f22.searchmodule.infrastructure  to javafx.fxml;
    opens dk.sdu.se_f22.searchmodule.misspellings    to javafx.fxml;
    opens dk.sdu.se_f22.searchmodule.onewaysynonyms  to javafx.fxml;
    opens dk.sdu.se_f22.searchmodule.twowaysynonyms  to javafx.fxml;
    opens dk.sdu.se_f22.sortingmodule.infrastructure to javafx.fxml;
    opens dk.sdu.se_f22.sortingmodule.range          to javafx.fxml;

}
