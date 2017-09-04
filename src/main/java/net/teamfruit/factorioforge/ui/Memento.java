package net.teamfruit.factorioforge.ui;

import net.teamfruit.factorioforge.factorioapi.data.IInfo;

public class Memento {
	private final String name;
	private IInfo info;

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

	@Override
	public String toString() {
		return String.format("Memento [text=%s]", this.name);
	}

}
