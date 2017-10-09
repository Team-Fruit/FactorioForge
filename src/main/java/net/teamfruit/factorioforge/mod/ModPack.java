package net.teamfruit.factorioforge.mod;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;

public class ModPack {

	private String name;
	private String author;
	private String base64Image;
	private transient Image image;
	private ObservableList<Info> mods = FXCollections.observableArrayList();

	public ModPack(final String name, final String author, final String base64image) {
		this.name = name;
		this.author = author;
		this.base64Image = base64image;
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

	public void setImage(final String base64Image) {
		this.base64Image = base64Image;
	}

	public Image getImage() {
		if (this.image!=null)
			return this.image;
		if (this.base64Image!=null)
			return this.image = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(this.base64Image)));
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
