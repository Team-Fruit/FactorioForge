package net.teamfruit.factorioforge.factorioapi.data;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.data.impl.MediaFile;
import net.teamfruit.factorioforge.factorioapi.data.impl.Release;

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
	List<MediaFile> getMediaFiles();

	/**
	 * A list of different versions of the mod available for download.
	 * @return List<IRelease>
	 */
	List<Release> getReleases();
}
