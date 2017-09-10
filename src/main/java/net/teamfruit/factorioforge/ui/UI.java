package net.teamfruit.factorioforge.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.teamfruit.factorioforge.mod.RepositoryManager;
import net.teamfruit.factorioforge.ui.UIRepository.UISpecificRepositoryProperty;

public class UI extends Application {
	public static final UISpecificRepositoryProperty<UIRootController> ROOT = UIRepository.key(UIRepository.STAGE);

	@Override
	public void init() throws Exception {
		// Do some heavy lifting
	}

	/**
	 * Launch Start Point
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		final FXMLLoader rootLoader = UIFactory.loadUI("UIRoot");

		final AnchorPane root = rootLoader.getRoot();
		final UIRootController rootController = rootLoader.getController();

		final Scene scene = new Scene(root);
		stage.setScene(scene);
		ROOT.set(scene, rootController);

		final AnchorPane view = UIFactory.loadUI("UIView").getRoot();
		rootController.getChildren().add(view);
		final AnchorPane login = UIFactory.loadUI("UILogin").getRoot();
		rootController.getChildren().add(login);

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
