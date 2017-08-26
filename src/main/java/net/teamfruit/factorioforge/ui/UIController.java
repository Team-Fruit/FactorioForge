package net.teamfruit.factorioforge.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UIController {
	@FXML
	public Label label;

	public void handleButtonAction(final ActionEvent event) {
		System.out.println("Controller: button clicked");
		this.label.setText(String.format("%f", Math.random()));
	}
}
