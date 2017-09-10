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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IInfo;
import net.teamfruit.factorioforge.mod.ModListConverter;
import net.teamfruit.factorioforge.mod.ModListManager;
import net.teamfruit.factorioforge.mod.RepositoryManager;

public class UIView {
	private AnchorPane uidetail;
	private UIModDetailController uidetailcontroller;
	@FXML
	private Text uidetailtitle;
	@FXML
	private VBox uidetailwrap;

	// 画面項目
	@FXML
	private Label typeLabel;
	@FXML
	private ListView<Memento> listView;
	private final ObservableList<Memento> listRecords = FXCollections.observableArrayList();

	@FXML
	private void initialize() throws IOException {
		final FXMLLoader moddetail = UIFactory.loadUI("UIModDetail");
		this.uidetail = moddetail.getRoot();
		this.uidetailcontroller = moddetail.getController();

		this.listView.setCellFactory(param -> new ModListCell());
		RepositoryManager.INSTANCE.thenAccept(o -> this.listView.refresh());
		this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			this.uidetailwrap.getChildren().clear();
			this.uidetailwrap.getChildren().add(this.uidetail);
			final IInfo info = newvalue.getInfo();

			this.uidetailtitle.setText(info.getTitle());
			this.uidetailcontroller.setInfo(info);
			this.uidetailcontroller.setStatus("Not implemented");
		});

		final Task<ObservableList<Memento>> task = new Task<ObservableList<Memento>>() {
			@Override
			protected ObservableList<Memento> call() throws Exception {
				final ObservableList<Memento> list = FXCollections.observableArrayList();
				//				updateValue(list);
				final Map<String, File> mods = ModListConverter.discoverModsDir(new File(FactorioForge.instance.factorioDir, "mods"));
				ModListManager.INSTANCE.getModList().mods.stream().forEach((mod) -> {
					final File modFile = mods.get(mod.name);
					if (modFile!=null)
						try {
							final IInfo info = ModListConverter.getModInfo(modFile);
							list.add(new Memento(mod.name).setInfo(info).setEnabled(mod.enabled));
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
				});
				return list;
			}
		};
		task.setOnSucceeded(wse -> UIView.this.listView.setItems(task.getValue()));
		RepositoryManager.INSTANCE.executor.submit(task);

		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		SmoothScroll.apply(this.textScroll);
		SmoothScroll.apply(this.modpackScroll);

		RepositoryManager.INSTANCE.thenAccept((modList) -> {

		});
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