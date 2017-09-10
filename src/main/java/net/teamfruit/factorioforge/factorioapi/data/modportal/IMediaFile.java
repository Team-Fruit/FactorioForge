package net.teamfruit.factorioforge.factorioapi.data.modportal;

public interface IMediaFile {

	/**
	 * A numerical ID unique to this media file.
	 * @return int
	 */
	int getId();

	/**
	 * Width of the full sized image in pixels.
	 * @return int
	 */
	int getWidth();

	/**
	 * Height of the full sized image in pixels.
	 * @return int
	 */
	int getHeight();

	/**
	 * Size of the full image in bytes.
	 * @return int
	 */
	int getSize();

	/**
	 * URLs to the full sized image and a thumbnail, see below.
	 * @return IMediaURL
	 */
	IMediaURL getURLs();

}
