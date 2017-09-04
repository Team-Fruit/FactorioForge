package net.teamfruit.factorioforge.ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import net.teamfruit.factorioforge.Log;

public class UIModCell {
	private boolean state;

	@FXML
	private BorderPane slidePane;
	@FXML
	private Button slideButton;
	@FXML
	private Pane slideBack;

	@FXML
	private void onSlideButtonClicked(final ActionEvent event) {
		setState(!getState());
	}

	@FXML
	private void initialize() {
		Log.log.info("{}, {}, {}", this.slidePane, this.slideButton, this.slideBack);
	}

	public void setState(final boolean state) {
		if (this.state!=state) {
			this.slideButton.setDisable(true);
			final TranslateTransition transition1 = new TranslateTransition();
			transition1.setNode(this.slideButton);
			transition1.setDuration(Duration.millis(100));
			transition1.setByX(this.state ? 26 : 0);
			transition1.setToX(this.state ? 0 : 26);
			transition1.setInterpolator(Interpolator.EASE_OUT);
			transition1.play();
			transition1.setOnFinished((ev) -> {
				this.slideButton.setText(this.state ? ">" : "<");
				this.state = state;
				this.slideButton.setDisable(false);
			});

			final Timeline transition2 = new Timeline();
			transition2.getKeyFrames().add(
					new KeyFrame(Duration.millis(100),
							new KeyValue(this.slideBack.prefWidthProperty(), this.state ? 13 : 26+13, Interpolator.EASE_OUT)));
			transition2.play();
		}
	}

	public boolean getState() {
		return this.state;
	}
}
