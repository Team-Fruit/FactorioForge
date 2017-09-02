package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ScrollEvent;
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
	private void initialize() {
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

		SmoothScroll.apply(this.textScroll);

		for (final Node node : this.listView.lookupAll("*"))
			if (node instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) node;
				SmoothScroll.apply(bar);
			}
		this.listView.setOnScroll((final ScrollEvent e) -> {
			Log.log.info(e);
		});
	}

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
			if (!empty)
				try {
					final AnchorPane cell = UIFactory.loadUI("UIModCell").getRoot();
					setGraphic(cell);
				} catch (final IOException e) {
					e.printStackTrace();
				}
		}
	}

	@FXML
	private ContextMenu menu;

	@FXML
	private void onEnableClicked(final ActionEvent event) {
		final ObservableList<Memento> items = this.listView.getSelectionModel().getSelectedItems();
		for (final Memento item : items) {

		}
	}

	@FXML
	private ScrollPane textScroll;
}