package dk.sdu.se_f22;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LauncherController {

	private List<File> guisInFolder(File folder) {
		if(!folder.isDirectory()) {
			throw new IllegalArgumentException("argument MUST be a directory.");
		}

		List<File> guis = new ArrayList<>();

		for (File containedFile : folder.listFiles()) {
			if (containedFile.isDirectory()) {
				guis.addAll(guisInFolder(containedFile));
			}
			if (containedFile.isFile()) {
				if (isGUI(containedFile))
					guis.add(containedFile);
			}
		}

		return guis;
	}

	private boolean isGUI(File file) {
		String[] parts = file.getAbsolutePath().split("\\.");
		String extension = parts[parts.length-1];
		return extension.equalsIgnoreCase("fxml");
	}

	private static void loadFXML(URL url) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent parent = fxmlLoader.load();

		Scene scene = new Scene(parent, 250, 150);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setMinHeight(200);
		stage.setMinWidth(340);
		stage.show();
	}

	@FXML ChoiceBox<File> guiSelector;

	@FXML
	public void initialize() {
		// Get GUIs from the group folders
		URL url = getClass().getResource("/dk/sdu/se_f22");
		if (url == null) {
			return;
		}

		File folder;
		try {
			folder = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}
		List<File> guis = guisInFolder(folder);

		for (File gui : guis) {
			guiSelector.setValue(gui);
		}
	}


	@FXML
	protected void onLaunchClicked(MouseEvent e) {
		// Get selected GUI
		File selectedGUI = guiSelector.getValue();
		// Launch GUI
		try {
			loadFXML(selectedGUI.toURI().toURL());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
