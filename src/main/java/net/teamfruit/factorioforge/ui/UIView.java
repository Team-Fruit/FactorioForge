package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.teamfruit.factorioforge.Log;

public class UIView {
	// 画面項目
	@FXML
	private Label typeLabel;
	@FXML
	private ListView<Memento> listView;
	private ObservableList<Memento> listRecords = FXCollections.observableArrayList();

	@FXML
	private void fill(final ActionEvent event) {
		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		for (int i = 0; i<100; i++) {
			final Memento me = new Memento(String.format("メメント%d", i));
			Log.log.info("メメント["+me+"]を追加します。");
			this.listRecords.add(me);
		}
		this.listView.setItems(this.listRecords);
		this.listView.setCellFactory(new Callback<ListView<Memento>, ListCell<Memento>>() {
			@Override
			public ListCell<Memento> call(final ListView<Memento> arg0) {
				return new MementoCell();
			}
		});
	}

	/**
	 * ListView<Memento>表示用のセル
	 */
	private static class MementoCell extends ListCell<Memento> {
		@Override
		protected void updateItem(final Memento me, final boolean empty) {
			super.updateItem(me, empty);
			final ListView<Memento> listView = listViewProperty().get();
			//listView.getSelectionModel().getSelectedItems();
			if (!empty) {
				setText(me.getProductionPath()+":"+me.hashCode());
				try {
					final AnchorPane cell = UIFactory.loadUI("UIModCell");
					setGraphic(cell);
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final int NUM = 9;

	@FXML
	private ContextMenu menu;

	@FXML
	private void click(final MouseEvent event) {
		if (event.isSecondaryButtonDown()) {
			final MenuItem[] items = new MenuItem[this.NUM];
			for (int i = 0; i<this.NUM; i++)
				items[i] = new MenuItem("item"+(i+1));
			for (int i = 0; i<this.NUM; i++)
				items[i].setOnAction(e -> {
					final MenuItem item = (MenuItem) e.getSource();
					//label.setText(item.getText());
				});

			this.menu.getItems().addAll(items);
		}
	}
}