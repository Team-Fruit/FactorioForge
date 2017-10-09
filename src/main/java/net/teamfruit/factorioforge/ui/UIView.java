package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.ModListConverter;
import net.teamfruit.factorioforge.mod.ModListManager;
import net.teamfruit.factorioforge.mod.ModPack;
import net.teamfruit.factorioforge.mod.ModPackManager;
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
	private final Label placeHolderLabel = new Label();
	private final ProgressIndicator placeHolderIndicator = new ProgressIndicator();
	private final ObservableList<Memento> listRecords = FXCollections.observableArrayList(memento -> new Observable[] {
			memento.enabledProperty(),
			memento.updateCheckedProperty(),
			memento.updateRequiredProperty(),
			memento.downloadStateProperty()
	});
	private final FilteredList<Memento> filterRecords = new FilteredList<>(this.listRecords);
	private final List<Memento> localMods = new ArrayList<>();
	private final List<Memento> remoteMods = new ArrayList<>();

	@FXML
	private void initialize() throws IOException {
		final FXMLLoader moddetail = UIFactory.loadUI("UIModDetail");
		this.uidetail = moddetail.getRoot();
		this.uidetailcontroller = moddetail.getController();

		this.listView.setCellFactory(param -> {
			final ModListCell cell = new ModListCell();

			cell.prefWidthProperty().bind(this.listView.widthProperty().subtract(20));
			cell.setMaxWidth(Region.USE_PREF_SIZE);

			final ContextMenu menu = new ContextMenu();
			final MenuItem enableItem = new MenuItem("Enable");
			final MenuItem disableItem = new MenuItem("Disable");
			final MenuItem downloadItem = new MenuItem("Download");
			final MenuItem deleteItem = new MenuItem("Delete");

			enableItem.setOnAction(ae -> {
				this.listView.getSelectionModel().getSelectedItems().forEach(memento -> memento.setEnabled(true));
				try {
					ModListManager.INSTANCE.save();
				} catch (final IOException e) {
					throw new UncheckedIOException(e);
				}
			});
			disableItem.setOnAction(ae -> {
				this.listView.getSelectionModel().getSelectedItems().forEach(memento -> memento.setEnabled(false));
				try {
					ModListManager.INSTANCE.save();
				} catch (final IOException e) {
					throw new UncheckedIOException(e);
				}
			});
			downloadItem.setOnAction(ae -> this.listView.getSelectionModel().getSelectedItems().forEach(memento -> memento.downloadMod()));
			deleteItem.setOnAction(ae -> this.listView.getSelectionModel().getSelectedItems().forEach(memento -> memento.deleteMod()));

			menu.getItems().addAll(enableItem, disableItem, downloadItem, deleteItem);
			menu.setOnShowing(we -> {
				if (!this.listView.getSelectionModel().isSelected(cell.getIndex())) {
					this.listView.getSelectionModel().clearSelection();
					this.listView.getSelectionModel().select(cell.getIndex());
				}
				final List<Memento> items = this.listView.getSelectionModel().getSelectedItems();
				enableItem.setVisible(items.stream().anyMatch(memento -> !memento.isEnabled()));
				disableItem.setVisible(items.stream().anyMatch(memento -> memento.isEnabled()));
				downloadItem.setVisible(items.stream().allMatch(memento -> !memento.isLocalMod()));
				deleteItem.setVisible(items.stream().allMatch(memento -> memento.isLocalMod()));
			});

			cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> cell.setContextMenu(isNowEmpty ? null : menu));
			return cell;
		});

		this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue!=null) {
				this.uidetailwrap.getChildren().clear();
				this.uidetailwrap.getChildren().add(this.uidetail);
				final Info info = newvalue.getInfo();

				this.uidetailtitle.setText(info.getTitle());
				this.uidetailcontroller.setInfo(info);
				this.uidetailcontroller.setStatus("Not implemented");
			} else {
				this.uidetailwrap.getChildren().clear();
				this.uidetailtitle.setText(null);
			}
		});

		final Task<List<Memento>> task = new Task<List<Memento>>() {
			@Override
			protected ObservableList<Memento> call() throws Exception {
				final Map<String, File> mods = ModListConverter.discoverModsDir(new File(FactorioForge.instance.factorioDir, "mods"));
				ModListManager.INSTANCE.getModList().mods.stream().forEach(mod -> {
					final File modFile = mods.get(mod.name);
					if (modFile!=null)
						try {
							final Info info = ModListConverter.getModInfo(modFile);
							final Memento memento = new Memento(mod.name).setLocalMod(mod).setInfo(info).setEnabled(mod.enabled);
							Platform.runLater(() -> {
								UIView.this.listRecords.add(memento);
								UIView.this.localMods.add(memento);
							});
						} catch (final IOException e) {
							throw new UncheckedIOException(e);
						}
				});
				return UIView.this.listRecords;
			}
		};
		UIView.this.listView.setItems(this.filterRecords);
		RepositoryManager.INSTANCE.executor.submit(task);

		this.placeHolderIndicator.setMaxSize(100, 100);

		this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.listView.setPlaceholder(this.placeHolderIndicator);

		//		SmoothScroll.apply(this.modpackScroll);

		RepositoryManager.INSTANCE.thenAccept(modList -> {
			this.filterPublic.setDisable(false);
			this.updateallbutton.setDisable(false);
			this.remoteMods.addAll(RepositoryManager.INSTANCE.getMementoes());
		});

		this.menulogin.setVisible(!ModDownloader.isUserDataProvided());
		this.menulogout.setVisible(ModDownloader.isUserDataProvided());
		ModDownloader.PROVIDED.addListener(v -> {
			this.menulogin.setVisible(!ModDownloader.isUserDataProvided());
			this.menulogout.setVisible(ModDownloader.isUserDataProvided());
		});

		this.modpacklist.setItems(ModPackManager.INSTANCE.getModpacks());
		this.modpacklist.setCellFactory(param -> new ModPackCell());
		this.modpacklist.prefHeightProperty().bind(Bindings.size(ModPackManager.INSTANCE.getModpacks()).multiply(58));

		ModPackManager.INSTANCE.getModpacks().addListener((ListChangeListener<ModPack>) c -> {
			final PauseTransition pause = new PauseTransition(Duration.millis(100));
			pause.setOnFinished(e -> UIView.this.modpackScroll.setVvalue(1d));
			pause.play();
		});
	}

	@FXML
	private ScrollPane modpackScroll;

	@FXML
	private TextField searchField;

	@FXML
	private void onSearchButtonClicked(final ActionEvent event) {
		final String searchText = this.searchField.getText();
		this.filterRecords.setPredicate(memento -> {
			if (StringUtils.startsWith(searchText, "\"")&&StringUtils.endsWith(searchText, "\"")) {
				final String s = StringUtils.substring(StringUtils.substring(searchText, 0, searchText.length()-1), 1);
				return StringUtils.equalsIgnoreCase(memento.getInfo().getTitle(), s)||StringUtils.equalsIgnoreCase(memento.getInfo().getName(), s);
			} else
				return StringUtils.containsIgnoreCase(memento.getInfo().getTitle(), searchText)||StringUtils.containsIgnoreCase(memento.getInfo().getName(), searchText);
		});
		this.placeHolderLabel.setText("検索条件に一致する項目はありません。");
		this.listView.setPlaceholder(this.placeHolderLabel);
		this.listView.getSelectionModel().clearSelection();
	}

	@FXML
	private CheckBox filterEnable;
	@FXML
	private CheckBox filterDisable;
	@FXML
	private CheckBox filterPublic;

	@FXML
	private void onFilterEnableClicked(final ActionEvent event) {
		filterEnable(this.filterEnable.isSelected(), this.filterDisable.isSelected());
	}

	@FXML
	private void onFilterDisableClicked(final ActionEvent event) {
		filterEnable(this.filterEnable.isSelected(), this.filterDisable.isSelected());
	}

	private void filterEnable(final boolean enable, final boolean disable) {
		if (enable&&disable)
			this.filterRecords.setPredicate(null);
		else if (enable)
			this.filterRecords.setPredicate(m -> m.getLocalMod().enabled);
		else if (disable)
			this.filterRecords.setPredicate(m -> !m.getLocalMod().enabled);
		else {
			this.placeHolderLabel.setText("フィルターに一致する項目はありません。");
			this.listView.setPlaceholder(this.placeHolderLabel);
			this.filterRecords.setPredicate(m -> false);
		}
		this.listView.getSelectionModel().clearSelection();
	}

	@FXML
	private void onFilterPublicClicked(final ActionEvent event) {
		final boolean select = this.filterPublic.isSelected();
		this.filterEnable.setDisable(select);
		this.filterDisable.setDisable(select);
		this.updateallbutton.setDisable(select);
		this.listRecords.clear();
		this.listView.getSelectionModel().clearSelection();
		this.listRecords.addAll(select ? this.remoteMods : this.localMods);
	}

	@FXML
	private Button updateallbutton;

	@FXML
	private void onUpdateAll(final ActionEvent e) {
		this.listView.getItems().stream().filter(m -> m.isUpdateRequired()).forEach(m -> m.downloadMod());
		//		final List<ProcessInfo> list = JProcesses.get().fastMode().listProcesses("factorio");
		//		for (final ProcessInfo info : list)
		//			JProcesses.killProcessGracefully(NumberUtils.toInt(info.getPid()));
		//		try {
		//			Desktop.getDesktop().browse(new URI("steam://rungameid/427520"));
		//		} catch (IOException|URISyntaxException e1) {
		//			e1.printStackTrace();
		//		}
	}

	@FXML
	private MenuButton mainmenu;
	@FXML
	private MenuItem menulogin;
	@FXML
	private MenuItem menulogout;

	@FXML
	private void onLogin(final ActionEvent event) throws IOException {
		final AnchorPane login = UIFactory.loadUI("UILogin").getRoot();
		UI.ROOT.get(this.uidetailwrap).getChildren().add(login);
	}

	@FXML
	private void onLogout(final ActionEvent event) {
		ModDownloader.setUser(null);
		RepositoryManager.INSTANCE.executor.submit(() -> new File(FactorioForge.instance.workingDir, "userdata.json").delete());
	}

	@FXML
	private void onExit(final ActionEvent e) {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private VBox modpacks;
	@FXML
	private ListView<ModPack> modpacklist;

	@FXML
	private void onNewModPack(final ActionEvent event) throws IOException {
		final FXMLLoader loader = UIFactory.loadUI("UINewModPack");
		final UINewModPackController controller = loader.getController();
		controller.setInitialMods(this.localMods.stream().map(m -> m.getInfo()).collect(Collectors.toList()));
		UI.ROOT.get(this.uidetailwrap).getChildren().add(loader.getRoot());
	}

}