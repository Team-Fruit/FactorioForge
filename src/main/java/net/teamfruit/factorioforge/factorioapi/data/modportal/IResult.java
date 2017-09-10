package net.teamfruit.factorioforge.factorioapi.data.modportal;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Tag;

public interface IResult extends IResponse {

	boolean isFullResult();

	IShortResult getShortResult();

	IFullResult getFullResult();

	/**
	 * The datetime the mod was uploaded, in the full ISO 8601 format, with a space separator instead of 'T'.
	 * @return String
	 */
	String getCreatedAt();

	/**
	 * @deprecated Doesn't seem to be implemented yet.
	 * @return Always null.
	 */
	@Deprecated
	String getCurrentUserRating();

	/**
	 * 	Number of downloads.
	 * @return int
	 */
	int getDownloadsCount();

	/**
	 * A list of major Factorio version strings (e.g. "0.13") starting with 0.13 that the mod is compatible with, in addition to the version(s) found in "latest_release" / "releases".
	 * @return List<String>
	 */
	List<String> getGameVersions();

	/**
	 * A link to the mod's github project page, just prepend "github.com/". Can be blank ("").
	 * @return String
	 */
	String getGitHubPath();

	/**
	 * 	Usually a URL to the mod's main project page, but can be any string.
	 * @return String
	 */
	String getHomepage();

	/**
	 * A numerical mod ID used to identify the mod in other API endpoints.
	 * @return int
	 */
	int getId();

	/**
	 * A bit field describing what permissions the mod's license grants.
	 * @return int
	 */
	int getLicenseFlags();

	/**
	 * The mod's license name.
	 * @return String
	 */
	String getLicenseName();

	/**
	 * A URL link to the full license agreement. Can be any string in case of custom licenses.
	 * @return String
	 */
	String getLicenseURL();

	/**
	 * The mod's machine-readable ID string.
	 * @return String
	 */
	String getName();

	/**
	 * The Factorio username of the mod's author.
	 * @return String
	 */
	String getOwner();

	/**
	 * @deprecated Doesn't seem to be implemented yet.
	 * @return Always 0
	 */
	@Deprecated
	int getRatingsCount();

	/**
	 * A shorter mod description.
	 * @return String
	 */
	String getSummary();

	/**
	 * A list of tag objects that categorize the mod.
	 * @return List<ITag>
	 */
	List<Tag> getTags();

	/**
	 * The mod's human-readable name.
	 * @return String
	 */
	String getTitle();

	/**
	 * The datetime the mod was last updated, in the full ISO 8601 format, with a space separator instead of 'T'.
	 * @return String
	 */
	String getUpdatedAt();

	/**
	 * The number of times the mod was viewed, but perhaps only counted on the web interface???
	 * @return int
	 */
	int getVisitsCount();
}
