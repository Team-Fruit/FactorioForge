package net.teamfruit.factorioforge.data;

import java.util.List;

public interface IFullResult extends IResult {

	/**
	 * A longer description of the mod, in text only format.
	 * @return String
	 */
	String getDescription();

	/**
	 * A longer description of the mod, with HTML tags.
	 * @return String
	 */
	String getDescriptionHTML();

	/**
	 * A list of media files, such as screen shots of the mod in action.
	 * @return List<MediaFile>
	 */
	List<IMediaFile> getMediaFiles();

	/**
	 * A list of different versions of the mod available for download.
	 * @return List<IRelease>
	 */
	List<IRelease> getReleases();
}
