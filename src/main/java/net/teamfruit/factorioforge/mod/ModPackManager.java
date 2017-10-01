package net.teamfruit.factorioforge.mod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModPackManager {

	public static final ModPackManager INSTANCE = new ModPackManager();

	private ObservableList<ModPack> modpacks = FXCollections.observableArrayList();

	private ModPackManager() {
	}

	public ObservableList<ModPack> getModpacks() {
		return this.modpacks;
	}

}
