package dk.sdu.se_f22;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	private List<LaunchableGUI> guisInFolder(File folder) {
		List<LaunchableGUI> guis = new ArrayList<>();

		for (File containedFile : folder.listFiles()) {
			if (containedFile.isDirectory()) {
				guis.addAll(guisInFolder(containedFile));
			}
			if (containedFile.isFile()) {
				if (isGUI(containedFile))
					guis.add(new LaunchableGUI(containedFile));
			}
		}

		return guis;
	}

	private boolean isGUI(File file) {
		String[] parts = file.getAbsolutePath().split("\\.");
		String extension = parts[parts.length-1];
		return extension.equalsIgnoreCase("fxml");
	}


	@FXML ChoiceBox<LaunchableGUI> guiSelector;
	ObservableList<LaunchableGUI> selectorItems;

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
		List<LaunchableGUI> guis = guisInFolder(folder);

		selectorItems = FXCollections.observableList(guis);
		guiSelector.setItems(selectorItems);
		guiSelector.setValue(selectorItems.get(0));
	}


	@FXML
	protected void onLaunchClicked(MouseEvent e) {
		// Get selected GUI
		LaunchableGUI selectedGUI = guiSelector.getValue();
		// Launch GUI
		try {
			selectedGUI.launch();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
