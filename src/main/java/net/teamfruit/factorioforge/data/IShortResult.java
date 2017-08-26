package net.teamfruit.factorioforge.data;

public interface IShortResult extends IResult {

	/**
	 * The first media file in the "media_files" list.
	 * @return IMediaFile
	 */
	IMediaFile getFirstMediaFile();

	/**
	 * The latest version of the mod available for download.
	 * @return IRelease
	 */
	IRelease getLatestRelease();

}
