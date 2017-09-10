package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class UILoginController {
	@FXML
	private AnchorPane rootpane;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;

	@FXML
	private void initialize() {
		this.rootpane.setOnKeyPressed(event -> {
			if (event.getCode()==KeyCode.ENTER)
				login(event.getSource());
		});
		this.username.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode()==KeyCode.TAB&&event.isShiftDown()) {
				event.consume();
				this.password.requestFocus();
			}
		});
		this.password.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode()==KeyCode.TAB) {
				event.consume();
				this.username.requestFocus();
			}
		});
	}

	@FXML
	private void onLogin(final ActionEvent event) throws IOException {
		login(event.getSource());
	}

	private void login(final Object o) {
		if (o instanceof Node) {
			final UIRootController root = UI.ROOT.get(o);
			root.getChildren().remove(this.rootpane);
		}
	}
}
