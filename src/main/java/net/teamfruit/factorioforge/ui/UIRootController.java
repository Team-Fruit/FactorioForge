package net.teamfruit.factorioforge.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class UIRootController {
	@FXML
	private StackPane rootPanel;

	public ObservableList<Node> getChildren() {
		return this.rootPanel.getChildren();
	}
}
