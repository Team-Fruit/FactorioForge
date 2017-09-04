package net.teamfruit.factorioforge.ui;

import net.teamfruit.factorioforge.factorioapi.data.IInfo;

public class Memento {
	private final String name;
	private IInfo info;

	private boolean enabled = false;
	private ModFileState fileState = ModFileState.LOCAL;

	public Memento(final String name) {
		this.name = name;
	}

	public Memento setInfo(final IInfo info) {
		this.info = info;
		return this;
	}

	public IInfo getInfo() {
		return this.info;
	}

	public Memento setEnabled(final boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public boolean isEnabled() {
		return this.enabled;
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
