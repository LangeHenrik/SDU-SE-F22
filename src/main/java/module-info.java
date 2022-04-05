module dk.sdu.se_f22 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires validatorfx;
	requires json.simple;
	requires java.sql;
	requires org.postgresql.jdbc;
    requires com.google.gson;
    requires org.jetbrains.annotations;
    opens dk.sdu.se_f22 to javafx.fxml;
	exports dk.sdu.se_f22;
}
