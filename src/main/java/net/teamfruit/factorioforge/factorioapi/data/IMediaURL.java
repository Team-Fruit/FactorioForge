package net.teamfruit.factorioforge.factorioapi.data;

public interface IMediaURL {

	/**
	 * URL to full sized image.
	 * @return String
	 */
	String getOriginal();

	/**
	 * URL to 128x128px sized thumbnail of image.
	 * @return String
	 */
	String getThumb();

}
