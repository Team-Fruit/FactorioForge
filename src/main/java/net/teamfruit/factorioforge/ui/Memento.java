package net.teamfruit.factorioforge.ui;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IRelease;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IShortResult;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.ModListBean.ModBean;
import net.teamfruit.factorioforge.mod.ModListConverter;
import net.teamfruit.factorioforge.mod.ModListManager;
import net.teamfruit.factorioforge.mod.RepositoryManager;

public class Memento {
	private final String name;
	private ModBean localmod;
	private Info info;

	private BooleanProperty enabled = new SimpleBooleanProperty(false);
	private BooleanProperty updateChecked = new SimpleBooleanProperty(false);
	private BooleanProperty updateRequired = new SimpleBooleanProperty(false);
	private ObjectProperty<DownloadState> downloadState = new SimpleObjectProperty<>(DownloadState.NONE);
	private ModDownloader currentTask;;

	public Memento(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Memento setInfo(final Info info) {
		this.info = info;
		RepositoryManager.INSTANCE.thenAccept(mods -> {
			final IShortResult r = RepositoryManager.INSTANCE.getResultByName(info.getName());
			this.updateChecked.set(true);
			if (r!=null&&!r.getLatestRelease().getVersion().equals(info.getVersion()))
				this.updateRequired.set(true);
		});
		return this;
	}

	public Info getInfo() {
		return this.info;
	}

	public Memento setLocalMod(final ModBean localmod) {
		this.localmod = localmod;
		return this;
	}

	public ModBean getLocalMod() {
		return this.localmod;
	}

	public boolean isLocalMod() {
		return getLocalMod()!=null;
	}

	public Memento setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
		if (this.localmod!=null)
			this.localmod.enabled = enabled;
		return this;
	}

	public Memento commitEnabled() {
		if (this.localmod!=null)
			try {
				ModListManager.INSTANCE.save();
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
		return this;
	}

	public boolean isEnabled() {
		return this.enabled.get();
	}

	public BooleanProperty enabledProperty() {
		return this.enabled;
	}

	public boolean isUpdateChecked() {
		return this.updateChecked.get();
	}

	public BooleanProperty updateCheckedProperty() {
		return this.updateChecked;
	}

	public Memento setUpdateRequired(final boolean bool) {
		this.updateRequired.set(bool);
		;
		return this;
	}

	public boolean isUpdateRequired() {
		return this.updateRequired.get();
	}

	public BooleanProperty updateRequiredProperty() {
		return this.updateRequired;
	}

	public Memento setCurrentTask(final ModDownloader task) {
		this.currentTask = task;
		return this;
	}

	public ModDownloader getCurrentTask() {
		return this.currentTask;
	}

	public DownloadState getDownloadState() {
		return this.downloadState.get();
	}

	public Memento setDownloadState(final DownloadState state) {
		this.downloadState.set(state);
		return this;
	}

	public ObjectProperty<DownloadState> downloadStateProperty() {
		return this.downloadState;
	}

	public ModDownloader downloadMod() {
		final IShortResult result = RepositoryManager.INSTANCE.getResultByName(getInfo().getName());
		if (result==null)
			throw new IllegalStateException();
		final IRelease release = result.getLatestRelease();
		final File file = new File(FactorioForge.instance.modsDir, release.getFileName());
		final ModDownloader task = new ModDownloader("https://mods.factorio.com"+release.getDownloadURL(), file,
				ModListConverter.discoverModsDir(FactorioForge.instance.modsDir).get(result.getName())) {

			@Override
			protected void succeeded() {
				if (!isLocalMod()) {
					final ModBean bean = new ModBean();
					bean.name = getName();
					bean.enabled = isEnabled();
					try {
						ModListManager.INSTANCE.add(bean).save();
					} catch (final IOException e) {
						setException(e);
						failed();
						return;
					}
					setLocalMod(bean);
				}
				setDownloadState(DownloadState.SUCCEEDED);
			}

			@Override
			protected void cancelled() {
				setDownloadState(DownloadState.CANCELLED);
			}

			@Override
			protected void failed() {
				setDownloadState(DownloadState.FAILED);
				Log.log.error(ExceptionUtils.getStackTrace(getException()));
			}
		};
		RepositoryManager.INSTANCE.executor.submit(task);
		setCurrentTask(task);
		setDownloadState(DownloadState.DOWNLOADING);
		return task;
	}

	public Task<Void> deleteMod() {
		if (!isLocalMod())
			throw new IllegalStateException();
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				final File file = ModListConverter.discoverModsDir(FactorioForge.instance.modsDir).get(getLocalMod().name);
				Files.delete(file.toPath());
				return null;
			}
		};
		RepositoryManager.INSTANCE.executor.submit(task);
		return task;
	}

	@Override
	public String toString() {
		return String.format("Memento [text=%s]", this.name);
	}

	public enum DownloadState {
		NONE,
		DOWNLOADING,
		SUCCEEDED,
		CANCELLED,
		FAILED
	}
}
