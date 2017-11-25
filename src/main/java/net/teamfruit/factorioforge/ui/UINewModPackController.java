package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;
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

	private List<Info> initialMods;

	@FXML
	private void initialize() {
		this.chooser = new FileChooser();
		this.chooser.setTitle("Open modpack");
		this.chooser.getExtensionFilters().add(new ExtensionFilter("FactorioForgeModPack", "*.ffmp", "*.json"));
	}

	@FXML
	private void onImport(final ActionEvent event) throws IOException {
		final File file = this.chooser.showOpenDialog(this.importmodpack.getScene().getWindow());
		if (file!=null)
			ModPackManager.INSTANCE.getModpacks().add(FactorioAPI.gson.fromJson(new FileReader(file), ModPack.class));
	}

	@FXML
	private void onCreate(final ActionEvent event) throws IOException {
		final UIRootController root = UI.ROOT.get(event.getSource());
		root.getChildren().remove(this.rootpane);
		final FXMLLoader loader = UIFactory.loadUI("UICreateModPack");
		final UICreateModPackController controller = loader.getController();
		controller.setInitialMods(this.initialMods);
		controller.setPrevGui(this.rootpane);
		root.getChildren().add(loader.getRoot());
	}

	public void setInitialMods(final List<Info> list) {
		this.initialMods = list;
	}
}
