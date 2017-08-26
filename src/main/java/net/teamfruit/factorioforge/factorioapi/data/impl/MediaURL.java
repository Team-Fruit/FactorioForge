package net.teamfruit.factorioforge.factorioapi.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.IMediaURL;

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
