package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.ModPack;
import net.teamfruit.factorioforge.mod.RepositoryManager;

public class UICreateModPackController {

	@FXML
	private AnchorPane rootpane;
	@FXML
	private Label namelabel;
	@FXML
	private TextField name;
	@FXML
	private TextField author;
	@FXML
	private Circle imageCircle;
	@FXML
	private Circle pileCircle;
	@FXML
	private Label changeicon;
	@FXML
	private Button delicon;

	private File image;
	private List<Info> initialMods;
	private Node prev;
	private FileChooser chooser;

	private final Stop[] stops = new Stop[] {
			new Stop(0, Color.gray(1, .2)),
			new Stop(1, Color.gray(.25, .8))
	};

	@FXML
	private void initialize() throws MalformedURLException {
		this.pileCircle.setFill(new RadialGradient(0, 0, .5, .5, .5, true, CycleMethod.NO_CYCLE, this.stops));
		this.chooser = new FileChooser();
		this.chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		this.chooser.getExtensionFilters().add(new ExtensionFilter("Picture", "*.jpg", "*.jpeg", "*.png", "*.gif"));
		this.author.setText(ModDownloader.getUser().getUsername());
	}

	@FXML
	private void onEnter(final MouseEvent event) {
		this.pileCircle.setVisible(true);
		this.changeicon.setVisible(true);
	}

	@FXML
	private void onExit(final MouseEvent event) {
		this.pileCircle.setVisible(false);
		this.changeicon.setVisible(false);
	}

	@FXML
	private void onOpenIcon(final MouseEvent event) throws IOException {
		this.image = this.chooser.showOpenDialog(this.changeicon.getScene().getWindow());
		this.pileCircle.setVisible(false);
		this.changeicon.setVisible(false);
		if (this.image!=null) {
			this.imageCircle.setFill(new ImagePattern(new Image(this.image.toURI().toURL().toString(), 128, 128, true, false)));
			this.delicon.setVisible(true);
		}
	}

	@FXML
	private void onBack(final ActionEvent event) {
		final UIRootController root = UI.ROOT.get(event.getSource());
		root.getChildren().add(this.prev);
		root.getChildren().remove(this.rootpane);
	}

	@FXML
	private void onDelete(final ActionEvent event) {
		this.image = null;
		this.imageCircle.setFill(Color.web("#7289da"));
		this.delicon.setVisible(false);
	}

	@FXML
	private void onCreate(final ActionEvent event) {
		final UIRootController root = UI.ROOT.get(event.getSource());
		root.getChildren().remove(this.rootpane);
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				final ModPack modpack = new ModPack(UICreateModPackController.this.name.getText(), UICreateModPackController.this.author.getText());
				modpack.getMods().addAll(UICreateModPackController.this.initialMods);
				if (UICreateModPackController.this.image!=null)
					modpack.setImage(Base64.getEncoder().encodeToString(IOUtils.toByteArray(new FileInputStream(UICreateModPackController.this.image))));
				return null;
			}
		};
		RepositoryManager.INSTANCE.executor.submit(task);
	}

	public void setInitialMods(final List<Info> list) {
		this.initialMods = list;
	}

	public void setPrevGui(final Node node) {
		this.prev = node;
	}
}
