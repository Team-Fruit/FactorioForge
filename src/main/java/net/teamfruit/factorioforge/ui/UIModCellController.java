package net.teamfruit.factorioforge.ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class UIModCellController {
	private boolean state;

	private Memento current;

	@FXML
	private BorderPane slidePane;
	@FXML
	private Button slideButton;
	@FXML
	private Pane slideBack;
	@FXML
	private Label label;
	@FXML
	private Button updateButton;
	@FXML
	private ProgressIndicator updateChecking;

	@FXML
	private void initialize() {

	}

	@FXML
	private void onSlideButtonClicked(final ActionEvent event) {
		setState(!getState(), false);
		if (this.current!=null)
			this.current.setEnabled(getState());
	}

	public void setState(final boolean state, final boolean fast) {
		if (this.state!=state)
			if (fast) {
				this.slideButton.setTranslateX(this.state ? 0 : 26);
				this.slideBack.prefWidthProperty().set(this.state ? 13 : 26+13);
				this.state = state;
			} else {
				this.slideButton.setDisable(true);
				final TranslateTransition transition1 = new TranslateTransition();
				transition1.setNode(this.slideButton);
				transition1.setDuration(Duration.millis(100));
				transition1.setByX(this.state ? 26 : 0);
				transition1.setToX(this.state ? 0 : 26);
				transition1.setInterpolator(Interpolator.EASE_OUT);
				transition1.play();
				transition1.setOnFinished((ev) -> {
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

	public void update(final Memento item) {
		this.current = item;
		setState(item.isEnabled(), true);
		this.label.setText(item.getInfo().getTitle());
		this.updateButton.setVisible(item.isUpdateRequired());
		this.updateChecking.setVisible(!item.isUpdateChecked());
	}
}
