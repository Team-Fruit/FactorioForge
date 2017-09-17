package net.teamfruit.factorioforge.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import net.teamfruit.factorioforge.ui.Memento.DownloadState;

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
	private ProgressBar progress;

	@FXML
	private void initialize() {
		this.updateButton.setMaxWidth(100);
		this.label.setTextOverrun(OverrunStyle.ELLIPSIS);
		this.label.setEllipsisString("...");
	}

	@FXML
	private void onSlideButtonClicked(final ActionEvent event) {
		setState(!getState(), false, () -> {
			if (this.current!=null)
				this.current.setEnabled(getState()).commitEnabled();
		});
	}

	public void setWidthProperty(final DoubleProperty property) {
		this.label.maxWidthProperty().bind(property.subtract(152));
	}

	public void setState(final boolean state, final boolean fast) {
		setState(state, fast, null);
	}

	public void setState(final boolean state, final boolean fast, final Runnable callback) {
		if (this.state!=state)
			if (fast) {
				this.slideButton.setTranslateX(this.state ? 0 : 26);
				this.slideBack.prefWidthProperty().set(this.state ? 13 : 26+13);
				this.state = state;
				if (callback!=null)
					callback.run();
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
					if (callback!=null)
						callback.run();
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

	@FXML
	private void onUpdateButtonClicked(final ActionEvent event) {
		this.current.runModDownloader();
	}

	public void update(final Memento item) {
		this.current = item;
		setState(item.isEnabled(), true);
		this.label.setText(item.getInfo().getTitle());
		this.updateButton.setVisible(item.getDownloadState()==DownloadState.NONE&&(item.isUpdateRequired()||!item.isLocalMod()));
		this.updateButton.setText(item.isLocalMod() ? "Update" : "Download");
		this.updateChecking.setVisible(!item.isUpdateChecked());
		switch (item.getDownloadState()) {
			case DOWNLOADING:
				this.updateButton.setVisible(false);
				this.progress.progressProperty().unbind();
				this.progress.progressProperty().bind(item.getCurrentTask().progressProperty());
				break;
			case FAILED:
				this.progress.lookup(".bar").setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
				this.updateButton.setText("Retry");
			case CANCELLED:
				this.updateButton.setVisible(true);
			case SUCCEEDED:
				final FadeTransition fade = new FadeTransition(new Duration(1000), this.progress);
				fade.setDelay(new Duration(1000));
				fade.setToValue(0);
				fade.setOnFinished(ae -> {
					this.progress.progressProperty().unbind();
					this.progress.progressProperty().set(0);
					this.progress.setOpacity(1);
					this.progress.lookup(".bar").setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
					item.setUpdateRequired(false);
					item.setDownloadState(DownloadState.NONE);
				});
				fade.play();
				break;
			default:
				this.progress.progressProperty().unbind();
				this.progress.progressProperty().set(0);
		}
	}

}
