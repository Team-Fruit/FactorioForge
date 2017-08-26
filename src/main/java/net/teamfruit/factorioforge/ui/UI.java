package net.teamfruit.factorioforge.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UI extends Application {
	@Override
	public void init() throws Exception {
		// Do some heavy lifting
	}

	/**
	 * Launch Start Point
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		final Pane root = new UIView();
		final Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Launch From Application
	 */
	public static void launchApplication() {
		Application.launch();
	}

	public static void main(final String[] args) {
		launchApplication();
	}
}
