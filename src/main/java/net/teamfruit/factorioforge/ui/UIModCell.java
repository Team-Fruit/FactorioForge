package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UIModCell extends AnchorPane {
	protected final UIModCellController controller;

	public UIModCell() throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("UIModCell.fxml"));
		loader.setRoot(this);
		// The following line is supposed to help Scene Builder, although it doesn't seem to be needed for me.
		loader.setClassLoader(getClass().getClassLoader());
		loader.load();

		this.controller = loader.getController();
	}
}
