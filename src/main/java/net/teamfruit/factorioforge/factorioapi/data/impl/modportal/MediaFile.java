package net.teamfruit.factorioforge.factorioapi.data.impl.modportal;

import net.teamfruit.factorioforge.factorioapi.data.modportal.IMediaFile;

public class MediaFile implements IMediaFile {

	private int id;
	private int width;
	private int height;
	private int size;
	private MediaURL urls;

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public MediaURL getURLs() {
		return this.urls;
	}

}
