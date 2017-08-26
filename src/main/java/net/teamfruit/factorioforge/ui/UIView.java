package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UIView extends AnchorPane {
	private final UIController controller;

	public UIView() throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("UIView.fxml"));
		loader.setRoot(this);
		// The following line is supposed to help Scene Builder, although it doesn't seem to be needed for me.
		loader.setClassLoader(getClass().getClassLoader());
		loader.load();

		this.controller = loader.getController();
	}

	public UIController getController() {
		return this.controller;
	}
}
