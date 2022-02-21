module dk.sdu.se_f22 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires validatorfx;
	requires org.postgresql.jdbc;
	requires commons.dbcp2;
	requires java.sql;
	requires java.management;
	requires org.apache.logging.log4j;
	requires org.apache.logging.log4j.core;

	opens dk.sdu.se_f22 to javafx.fxml;
	exports dk.sdu.se_f22;
	exports dk.sdu.se_f22.dao;
	opens dk.sdu.se_f22.dao to javafx.fxml;
}