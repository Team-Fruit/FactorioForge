package net.teamfruit.factorioforge.mod;

import java.io.ByteArrayInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;

public class ModPack {

	private String name;
	private String author;
	private byte[] rawimage;
	private Image image;
	private ObservableList<Info> mods = FXCollections.observableArrayList();

	public ModPack(final String name, final String author, final byte[] image) {
		this.name = name;
		this.author = author;
		this.rawimage = image;
	}

	public ModPack(final String name, final String author) {
		this(name, author, null);
	}

	public String getName() {
		return this.name;
	}

	public String getAuthor() {
		return this.author;
	}

	public Image getImage() {
		if (this.image!=null)
			return this.image;
		if (this.rawimage!=null)
			return this.image = new Image(new ByteArrayInputStream(this.rawimage));
		return null;
	}

	public ObservableList<Info> getMods() {
		return this.mods;
	}

	public ModPack addMod(final Info info) {
		this.mods.add(info);
		return this;
	}

	public ModPack remove(final Info info) {
		this.mods.remove(info);
		return this;
	}

	public ModPack remove(final String name) {
		this.mods.removeIf(info -> info.getName().equals(name));
		return this;
	}

}
