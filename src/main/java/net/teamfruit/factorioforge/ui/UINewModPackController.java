package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.mod.ModPack;
import net.teamfruit.factorioforge.mod.ModPackManager;

public class UINewModPackController {

	private FileChooser chooser;
	@FXML
	private AnchorPane rootpane;
	@FXML
	private Button createmodpack;
	@FXML
	private Button importmodpack;

	@FXML
	private void initialize() {
		this.chooser = new FileChooser();
		this.chooser.setTitle("Open modpack");
		this.chooser.getExtensionFilters().add(new ExtensionFilter("FactorioForgeModPack", "*.ffmp"));
	}

	@FXML
	private void onImport(final ActionEvent event) {
		final File file = this.chooser.showOpenDialog(this.importmodpack.getScene().getWindow());
		if (file!=null)
			try {
				ModPackManager.INSTANCE.getModpacks().add(FactorioAPI.gson.fromJson(new FileReader(file), ModPack.class));
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
	}

	@FXML
	private void onCreate(final ActionEvent event) throws IOException {
		final UIRootController root = UI.ROOT.get(event.getSource());
		root.getChildren().remove(this.rootpane);
		final AnchorPane create = UIFactory.loadUI("UICreateModPack").getRoot();
		root.getChildren().add(create);
	}
}
