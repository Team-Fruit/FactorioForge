package net.teamfruit.factorioforge.ui;

public class Memento {
	private final String text;

	public Memento(final String text) {
		this.text = text;
	}

	public String getProductionPath() {
		return this.text;
	}

	@Override
	public String toString() {
		return String.format("Memento [text=%s]", this.text);
	}

}
