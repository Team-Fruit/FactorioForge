package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class UILoginController {
	//	@FXML
	//	private AnchorPane root;

	@FXML
	private void initialize() {

	}

	@FXML
	private void onLogin(final ActionEvent event) throws IOException {
		final Object o = event.getSource();
		if (o instanceof Node) {
			final UIRootController root = UI.ROOT.get(event.getSource());
			;//root.getChildren().remove(root);
		}
	}
}
