package net.teamfruit.factorioforge.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
import net.teamfruit.factorioforge.ui.Memento.ModFileState;

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
	private List<Memento> localMods;
	private List<Memento> remoteMods;

	@FXML
	private void initialize() throws IOException {
		final FXMLLoader moddetail = UIFactory.loadUI("UIModDetail");
		this.uidetail = moddetail.getRoot();
		this.uidetailcontroller = moddetail.getController();

		this.listView.setCellFactory(param -> new ModListCell());
		this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			this.uidetailwrap.getChildren().clear();
			this.uidetailwrap.getChildren().add(this.uidetail);
			final IInfo info = newvalue.getInfo();

			this.uidetailtitle.setText(info.getTitle());
			this.uidetailcontroller.setInfo(info);
			this.uidetailcontroller.setStatus("Not implemented");
		});

		final Task<List<Memento>> task = new Task<List<Memento>>() {
			@Override
			protected List<Memento> call() throws Exception {
				final List<Memento> list = new ArrayList<>();
				//				updateValue(list);
				final Map<String, File> mods = ModListConverter.discoverModsDir(new File(FactorioForge.instance.factorioDir, "mods"));
				ModListManager.INSTANCE.getModList().mods.stream().forEach((mod) -> {
					final File modFile = mods.get(mod.name);
					if (modFile!=null)
						try {
							final IInfo info = ModListConverter.getModInfo(modFile);
							list.add(new Memento(mod.name).setLocalMod(mod).setInfo(info).setEnabled(mod.enabled));
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
				});
				return list;
			}
		};
		task.setOnSucceeded(wse -> {
			this.localMods = task.getValue();
			this.listRecords.addAll(task.getValue());
		});
		UIView.this.listView.setItems(this.listRecords);
		RepositoryManager.INSTANCE.executor.submit(task);

		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		SmoothScroll.apply(this.textScroll);
		SmoothScroll.apply(this.modpackScroll);

		RepositoryManager.INSTANCE.thenAccept(modList -> {
			this.listView.refresh();
			this.filterPublic.setDisable(false);
			this.updateallbutton.setDisable(false);
			this.remoteMods = RepositoryManager.INSTANCE.getMementoes();
			task.setOnScheduled(wse -> this.remoteMods.stream().forEach(memento -> {
				try {
					ModListManager.INSTANCE.getModList().mods.stream()
							.filter(local -> local.name.equals(memento.getInfo().getTitle())).findAny().ifPresent(bean -> {
								memento.setLocalMod(bean);
								memento.setModFileState(ModFileState.LOCAL);
								memento.setEnabled(bean.enabled);
							});
				} catch (final IOException e) {
					throw new UncheckedIOException(e);
				}
			}));
		});
	}

	@FXML
	private ContextMenu menu;

	@FXML
	private void onMenuOpened(final ActionEvent event) {
		this.listView.getSelectionModel().getSelectedItems().stream().forEach(memento -> {

		});
	}

	@FXML
	private void onEnableClicked(final ActionEvent event) {
		final boolean local = !this.filterPublic.isSelected();
		this.menuEnable.setVisible(local);
		this.menuDisable.setVisible(local);
		this.menuDelete.setVisible(local);
		if (local) {
			if (this.listView.getSelectionModel().getSelectedItems().stream().anyMatch(Memento::isUpdateRequired))
				this.menuUpdate.setVisible(true);
		} else
			this.menuUpdate.setVisible(false);
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
	private void onFilterEnableClicked(final ActionEvent event) {

	}

	@FXML
	private void onFilterDisableClicked(final ActionEvent event) {

	}

	@FXML
	private void onFilterCachedClicked(final ActionEvent event) {

	}

	@FXML
	private void onFilterPublicClicked(final ActionEvent event) {
		final boolean select = this.filterPublic.isSelected();
		this.filterEnable.setDisable(select);
		this.filterDisable.setDisable(select);
		this.listRecords.clear();
		this.listRecords.addAll(select ? this.remoteMods : this.localMods);
		clearDetail();
	}

	private void clearDetail() {
		this.uidetailwrap.getChildren().clear();
		this.uidetailtitle.setText(null);
	}

	@FXML
	private MenuItem menuEnable;
	@FXML
	private MenuItem menuDisable;
	@FXML
	private MenuItem menuDownload;
	@FXML
	private MenuItem menuDelete;
	@FXML
	private MenuItem menuUpdate;

	@FXML
	private Button updateallbutton;

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