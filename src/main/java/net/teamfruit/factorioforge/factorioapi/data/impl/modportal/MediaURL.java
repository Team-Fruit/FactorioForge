package net.teamfruit.factorioforge.factorioapi.data.impl.modportal;

import net.teamfruit.factorioforge.factorioapi.data.modportal.IMediaURL;

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
