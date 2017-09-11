package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.commons.lang3.StringUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.auth.IToken;
import net.teamfruit.factorioforge.factorioapi.data.impl.auth.Token;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.RepositoryManager;

public class UILoginController {
	@FXML
	private AnchorPane rootpane;
	@FXML
	private Button login;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private CheckBox rememberMe;
	@FXML
	private Label label;
	@FXML
	private ProgressIndicator indicator;

	@FXML
	private void initialize() {
		this.rootpane.setOnKeyPressed(event -> {
			if (event.getCode()==KeyCode.ENTER&&!this.login.isDisable())
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
		Platform.runLater(() -> this.username.requestFocus());
	}

	@FXML
	private void onLogin(final ActionEvent event) throws IOException {
		login(event.getSource());
	}

	private void login(final Object o) {
		if (StringUtils.isNotBlank(this.username.getText())&&StringUtils.isNotBlank(this.password.getText())) {
			final Task<IToken> task = new Task<IToken>() {
				@Override
				protected IToken call() throws Exception {
					final IToken token = FactorioAPI.getAuthToken(UILoginController.this.username.getText(), UILoginController.this.password.getText());
					updateProgress(1, 1);
					return token;
				}
			};
			task.setOnSucceeded(wse -> {
				this.indicator.setVisible(false);
				this.login.setDisable(false);
				final IToken token = task.getValue();
				if (token.isError())
					showMessage(token.getErrorMessage());
				else {
					if (this.rememberMe.isSelected()) {
						final File file = new File(FactorioForge.instance.workingDir, "token.json");
						try (FileWriter w = new FileWriter(file)) {
							FactorioAPI.gson.toJson(token, Token.class, w);
							ModDownloader.setToken(token.getToken());
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
					}
					if (o instanceof Node) {
						final UIRootController root = UI.ROOT.get(o);
						root.getChildren().remove(this.rootpane);
					}
				}
			});
			task.setOnFailed(wse -> {
				task.getException().printStackTrace();
				showMessage(task.getException().getClass().getSimpleName());
				this.indicator.setVisible(false);
				this.login.setDisable(false);
			});
			this.indicator.progressProperty().bind(task.progressProperty());
			this.indicator.setVisible(true);
			this.login.setDisable(true);
			RepositoryManager.INSTANCE.executor.submit(task);

		} else
			showMessage("Username and password can not be empty");
	}

	private void showMessage(final String message) {
		this.rememberMe.setVisible(false);
		this.label.setText(message);
		this.label.setVisible(true);
		new Timeline(new KeyFrame(Duration.millis(3000), event -> {
			this.label.setVisible(false);
			this.rememberMe.setVisible(true);
		})).play();
	}
}
