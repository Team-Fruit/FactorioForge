package net.teamfruit.factorioforge.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.RepositoryManager;
import net.teamfruit.factorioforge.factorioapi.data.IInfo;
import net.teamfruit.factorioforge.mod.ModListConverter;

public class UIView {
	// 画面項目
	@FXML
	private Label typeLabel;
	@FXML
	private ListView<Memento> listView;
	private ObservableList<Memento> listRecords = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		this.listView.setCellFactory(param -> new ModListCell());

		final Task<ObservableList<Memento>> task = new Task<ObservableList<Memento>>() {
			@Override
			protected ObservableList<Memento> call() throws Exception {
				final ObservableList<Memento> list = FXCollections.observableArrayList();
				//				updateValue(list);
				final Map<String, File> mods = ModListConverter.discoverModsDir(new File(FactorioForge.instance.factorioDir, "mods"));
				ModListConverter.getModList(new File(FactorioForge.instance.factorioDir, "mods\\mod-list.json")).mods.stream().forEach((mod) -> {
					final File modFile = mods.get(mod.name);
					if (modFile!=null)
						try {
							final IInfo info = ModListConverter.getModInfo(modFile);
							list.add(new Memento(mod.name).setInfo(info));
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
				});
				return list;
			}
		};
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent event) {
				UIView.this.listView.setItems(task.getValue());
			}
		});
		RepositoryManager.instance.executor.submit(task);

		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		SmoothScroll.apply(this.textScroll);
		SmoothScroll.apply(this.modpackScroll);
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
	@FXML
	private ScrollPane modpackScroll;

	@FXML
	private CheckBox filterEnable;
	@FXML
	private CheckBox filterDisable;
	@FXML
	private CheckBox filterCached;
	@FXML
	private CheckBox filterPublic;

	@FXML
	private void onUpdateAll(final ActionEvent e) {
		final List<ProcessInfo> list = JProcesses.get().fastMode().listProcesses("factorio");
		for (final ProcessInfo info : list)
			JProcesses.killProcessGracefully(NumberUtils.toInt(info.getPid()));
		try {
			Desktop.getDesktop().browse(new URI("steam://rungameid/427520"));
		} catch (IOException|URISyntaxException e1) {
			e1.printStackTrace();
		}
	}
}