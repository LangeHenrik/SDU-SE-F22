module dk.sdu.se_f22 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires validatorfx;

	opens dk.sdu.se_f22 to javafx.fxml;
	exports dk.sdu.se_f22;
}