package net.teamfruit.factorioforge.data.impl;

import net.teamfruit.factorioforge.data.IMediaURL;

public class MediaURL implements IMediaURL {

	private String original;
	private String thumb;

	@Override
	public String getOriginal() {
		return this.original;
	}

	@Override
	public String getThumb() {
		return this.thumb;
	}

}
