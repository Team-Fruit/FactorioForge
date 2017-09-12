package net.teamfruit.factorioforge.ui;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IRelease;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.ModListConverter;
import net.teamfruit.factorioforge.mod.RepositoryManager;
import net.teamfruit.factorioforge.ui.Memento.ModFileState;

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

	}

	@FXML
	private void onSlideButtonClicked(final ActionEvent event) {
		setState(!getState(), false, () -> {
			if (this.current!=null)
				this.current.setEnabled(getState()).commitEnabled();
		});
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
		RepositoryManager.INSTANCE.getResultByName(this.current.getInfo().getName()).ifPresent(result -> {
			final IRelease release = result.getLatestRelease();
			final ModDownloader task = new ModDownloader("https://mods.factorio.com"+release.getDownloadURL(),
					new File(FactorioForge.instance.modsDir, release.getFileName()),
					ModListConverter.discoverModsDir(FactorioForge.instance.modsDir).get(result.getName()));
			setTask(task);
			RepositoryManager.INSTANCE.executor.submit(task);
			this.current.setModFileState(ModFileState.DOWNLOADING);
			this.current.setCurrentTask(task);
		});
	}

	public void update(final Memento item) {
		this.current = item;
		setState(item.isEnabled(), true);
		this.label.setText(item.getInfo().getTitle());
		this.updateButton.setVisible(item.isUpdateRequired());
		this.updateChecking.setVisible(!item.isUpdateChecked());
		if (item.getModFileState()==ModFileState.DOWNLOADING) {
			setTask(item.getCurrentTask());
		} else {
			this.progress.progressProperty().unbind();
			this.progress.progressProperty().set(0);
			item.setCurrentTask(null);
		}
	}

	private void setTask(final ModDownloader task) {
		this.updateButton.setVisible(false);
		task.setOnSucceeded(e -> {
			this.progress.progressProperty().unbind();
			final FadeTransition fade = new FadeTransition(new Duration(1000), this.progress);
			fade.setDelay(new Duration(1000));
			fade.setToValue(0);
			fade.setOnFinished(ae -> {
				this.progress.progressProperty().set(0);
				this.progress.setOpacity(1);
			});
			fade.play();
			this.current.setUpdateRequired(false);
			this.current.setModFileState(ModFileState.LOCAL);
		});
		task.setOnCancelled(e -> {
			this.updateButton.setVisible(true);
			this.current.setModFileState(ModFileState.LOCAL);
		});
		task.setOnFailed(e -> {
			this.progress.lookup(".bar").setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
			this.updateButton.setText("Retry");
			this.updateButton.setVisible(true);
			this.current.setModFileState(ModFileState.LOCAL);
		});
		this.progress.progressProperty().unbind();
		this.progress.progressProperty().bind(task.progressProperty());
	}

}
