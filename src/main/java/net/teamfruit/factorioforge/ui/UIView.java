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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

import javafx.application.Platform;
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
import javafx.scene.control.TextField;
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
	private final Label placeHolder = new Label();
	private final ObservableList<Memento> listRecords = FXCollections.observableArrayList();
	private final List<Memento> enableMods = new ArrayList<>();
	private final List<Memento> disableMods = new ArrayList<>();
	private final List<Memento> localMods = new ArrayList<>();
	private final List<Memento> remoteMods = new ArrayList<>();

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
			protected ObservableList<Memento> call() throws Exception {
				final Map<String, File> mods = ModListConverter.discoverModsDir(new File(FactorioForge.instance.factorioDir, "mods"));
				ModListManager.INSTANCE.getModList().mods.stream().forEach((mod) -> {
					final File modFile = mods.get(mod.name);
					if (modFile!=null)
						try {
							final IInfo info = ModListConverter.getModInfo(modFile);
							final Memento memento = new Memento(mod.name).setLocalMod(mod).setInfo(info).setEnabled(mod.enabled);
							Platform.runLater(() -> {
								UIView.this.listRecords.add(memento);
								UIView.this.localMods.add(memento);
								if (mod.enabled)
									UIView.this.enableMods.add(memento);
								else
									UIView.this.disableMods.add(memento);
							});
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
				});
				return UIView.this.listRecords;
			}
		};
		UIView.this.listView.setItems(this.listRecords);
		RepositoryManager.INSTANCE.executor.submit(task);

		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.listView.setPlaceholder(this.placeHolder);

		SmoothScroll.apply(this.textScroll);
		SmoothScroll.apply(this.modpackScroll);

		RepositoryManager.INSTANCE.thenAccept(modList -> {
			this.listView.refresh();
			this.filterPublic.setDisable(false);
			this.updateallbutton.setDisable(false);
			this.remoteMods.addAll(RepositoryManager.INSTANCE.getMementoes());
			//			task.setOnScheduled(wse -> this.remoteMods.stream().forEach(memento -> {
			//				Log.log.info("hey");
			//				try {
			//					ModListManager.INSTANCE.getModList().mods.stream()
			//							.filter(local -> local.name.equals(memento.getInfo().getTitle())).findAny().ifPresent(bean -> {
			//								memento.setLocalMod(bean);
			//								memento.setModFileState(ModFileState.LOCAL);
			//								memento.setEnabled(bean.enabled);
			//							});
			//				} catch (final IOException e) {
			//					throw new UncheckedIOException(e);
			//				}
			//			}));
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
	private TextField searchField;
	private String searchText;

	@FXML
	private void onSearchButtonClicked(final ActionEvent event) {
		this.searchText = this.searchField.getText();
		filter(this.searchText);
	}

	@FXML
	private CheckBox filterEnable;
	@FXML
	private CheckBox filterDisable;
	@FXML
	private CheckBox filterPublic;

	@FXML
	private void onFilterEnableClicked(final ActionEvent event) {
		filter(this.searchText);
	}

	@FXML
	private void onFilterDisableClicked(final ActionEvent event) {
		filter(this.searchText);
	}

	private void filter(final String str) {
		final boolean enable = this.filterEnable.isSelected();
		final boolean disable = this.filterDisable.isSelected();
		if (StringUtils.isBlank(str)&&!this.filterPublic.isSelected()) {
			if (enable&&disable) {
				this.listRecords.clear();
				this.listRecords.addAll(this.localMods);
			} else if (enable)
				this.listRecords.removeAll(this.disableMods);
			else if (disable)
				this.listRecords.removeAll(this.enableMods);
			else
				this.placeHolder.setText("フィルターに一致する項目はありません。");
		} else {
			//TODO StackOverFlow
			filter(null);
			this.listRecords.removeIf(memento -> {
				if (StringUtils.startsWith(str, "\"")&&StringUtils.endsWith(str, "\"")) {
					final String s = StringUtils.substring(StringUtils.substring(str, 0, str.length()-1), 1);
					return !(StringUtils.equalsIgnoreCase(memento.getInfo().getTitle(), s)||StringUtils.equalsIgnoreCase(memento.getInfo().getName(), s));
				} else
					return !(StringUtils.containsIgnoreCase(memento.getInfo().getTitle(), str)||StringUtils.containsIgnoreCase(memento.getInfo().getName(), str));
			});
			this.placeHolder.setText("検索条件に一致する項目はありません。");
		}
		clearDetail();
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