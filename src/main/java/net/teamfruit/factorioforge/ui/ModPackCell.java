package net.teamfruit.factorioforge.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import net.teamfruit.factorioforge.mod.ModPack;

public class ModPackCell extends ListCell<ModPack> {

	private final AnchorPane root = new AnchorPane();
	private final Button button = new Button();

	public ModPackCell() {
		this.button.getStyleClass().add("modpack");
		this.button.setPrefWidth(54);
		this.button.setPrefHeight(54);

		//		AnchorPane.setBottomAnchor(this.button, 3d);
		//		AnchorPane.setLeftAnchor(this.button, 3d);
		//		AnchorPane.setRightAnchor(this.button, 3d);
		//		AnchorPane.setTopAnchor(this.button, 3d);
		//
		this.root.getChildren().add(this.button);
	}

	@Override
	protected void updateItem(final ModPack item, final boolean empty) {
		super.updateItem(item, empty);
		if (empty||item==null)
			setGraphic(null);
		else
			setGraphic(this.root);
		setStyle("-fx-background-color: transparent;-fx-padding: 0;");

	}
}
