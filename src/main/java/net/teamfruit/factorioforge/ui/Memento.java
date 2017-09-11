package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import net.teamfruit.factorioforge.factorioapi.data.modportal.IInfo;
import net.teamfruit.factorioforge.mod.ModListBean.ModBean;
import net.teamfruit.factorioforge.mod.ModListManager;
import net.teamfruit.factorioforge.mod.RepositoryManager;

public class Memento {
	private final String name;
	private ModBean localmod;
	private IInfo info;

	private boolean enabled = false;
	private boolean updateChecked = false;
	private boolean updateRequired = false;
	private ModFileState fileState = ModFileState.LOCAL;

	public Memento(final String name) {
		this.name = name;
	}

	public Memento setInfo(final IInfo info) {
		this.info = info;
		RepositoryManager.INSTANCE.thenAccept(mods -> RepositoryManager.INSTANCE.getResultByName(info.getName()).ifPresent(r -> {
			this.updateChecked = true;
			if (!r.getLatestRelease().getVersion().equals(info.getVersion()))
				this.updateRequired = true;
		}));
		return this;
	}

	public IInfo getInfo() {
		return this.info;
	}

	public Memento setLocalMod(final ModBean localmod) {
		this.localmod = localmod;
		return this;
	}

	public ModBean getLocalMod() {
		return this.localmod;
	}

	public Memento setEnabled(final boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public Memento commitEnabled() {
		if (this.localmod!=null) {
			this.localmod.enabled = isEnabled();
			try {
				ModListManager.INSTANCE.save();
			} catch (final IOException e) {
			}
		}
		return this;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isUpdateChecked() {
		return this.updateChecked;
	}

	public boolean isUpdateRequired() {
		return this.updateRequired;
	}

	public Memento setModFileState(final ModFileState fileState) {
		this.fileState = fileState;
		return this;
	}

	public ModFileState getModFileState() {
		return this.fileState;
	}

	@Override
	public String toString() {
		return String.format("Memento [text=%s]", this.name);
	}

	public enum ModFileState {
		LOCAL,
		DOWNLOADING,
		REMOTE,
	}
}
