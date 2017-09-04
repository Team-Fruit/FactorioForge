package net.teamfruit.factorioforge.ui;

import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class ModListCell extends ListCell<Memento> {

	private UIModCellController controller;

	@Override
	protected void updateItem(final Memento item, final boolean empty) {
		super.updateItem(item, empty);

		if (empty||item==null) {
			setGraphic(null);
			setText(null);
			this.controller = null;
		} else {
			if (this.controller==null||getGraphic()==null) {
				try {
					final FXMLLoader loader = UIFactory.getUI("UIModCell");
					final Node node = loader.load();
					this.controller = loader.getController();
					setGraphic(node);
				} catch (final IOException e) {
					e.printStackTrace();
					throw new UncheckedIOException(e);
				}
			}
			this.controller.update(item);
		}
	}

}
