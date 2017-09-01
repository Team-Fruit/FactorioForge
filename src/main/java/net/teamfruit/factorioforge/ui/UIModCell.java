package net.teamfruit.factorioforge.ui;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class UIModCell {
	private boolean state;

	@FXML
	private BorderPane slidePane;
	@FXML
	private Button slideButton;

	@FXML
	private void onSlideButtonClicked(final ActionEvent event) {
		setState(!getState());
	}

	public void setState(final boolean state) {
		if (this.state!=state) {
			this.slideButton.setDisable(true);
			final TranslateTransition transition = new TranslateTransition();
			transition.setNode(this.slidePane);
			transition.setDuration(Duration.millis(100));
			transition.setByX(this.state ? 30 : 0);
			transition.setToX(this.state ? 0 : 30);
			transition.setInterpolator(Interpolator.EASE_OUT);
			transition.playFromStart();
			transition.setOnFinished((ev) -> {
				this.slideButton.setText(this.state ? ">" : "<");
				this.state = state;
				this.slideButton.setDisable(false);
			});
		}
	}

	public boolean getState() {
		return this.state;
	}
}
