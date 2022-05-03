open module dk.sdu.se_f22 {
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
    requires org.apache.commons.pool2;

    requires org.jetbrains.annotations;
    opens dk.sdu.se_f22 to javafx.fxml;
    opens dk.sdu.se_f22.searchmodule.infrastructure to javafx.fxml;

    exports dk.sdu.se_f22;

    exports dk.sdu.se_f22.searchmodule.infrastructure;
    exports dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

    opens dk.sdu.se_f22.searchmodule.infrastructure.GUI to javafx.fxml;
    exports dk.sdu.se_f22.searchmodule.infrastructure.GUI;
    exports dk.sdu.se_f22.searchmodule.infrastructure.logger;
    opens dk.sdu.se_f22.searchmodule.infrastructure.logger to javafx.fxml;
    exports dk.sdu.se_f22.searchmodule.infrastructure.util;
    opens dk.sdu.se_f22.searchmodule.infrastructure.util to javafx.fxml;
    exports dk.sdu.se_f22.searchmodule.infrastructure.tokenization;
    opens dk.sdu.se_f22.searchmodule.infrastructure.tokenization to javafx.fxml;
}
