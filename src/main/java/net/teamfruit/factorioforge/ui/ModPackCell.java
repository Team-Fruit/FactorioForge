package net.teamfruit.factorioforge.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import net.teamfruit.factorioforge.mod.ModPack;

public class ModPackCell extends ListCell<ModPack> {

	private final Button button = new Button();

	public ModPackCell() {
		this.button.getStyleClass().add("modpack");
		this.button.setPrefWidth(54);
		this.button.setPrefHeight(54);
	}

	@Override
	protected void updateItem(final ModPack item, final boolean empty) {
		super.updateItem(item, empty);
		if (empty||item==null)
			setGraphic(null);
		else
			setGraphic(this.button);
		setStyle("-fx-background-color: transparent;-fx-padding: 1.5px;");

	}
}
