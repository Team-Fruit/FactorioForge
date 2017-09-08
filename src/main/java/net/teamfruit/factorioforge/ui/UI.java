package net.teamfruit.factorioforge.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.teamfruit.factorioforge.mod.RepositoryManager;

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
		final AnchorPane root = UIFactory.loadUI("UIView").getRoot();

		final Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(we -> RepositoryManager.INSTANCE.executor.shutdown());
	}

	/**
	 * Launch From Application
	 */
	public static void launchApplication() {
		Application.launch();
	}

	@Deprecated
	public static void main(final String[] args) {
		launchApplication();
	}
}
