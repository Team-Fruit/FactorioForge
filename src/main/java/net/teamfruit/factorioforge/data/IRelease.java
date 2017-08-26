package net.teamfruit.factorioforge.data;

public interface IRelease {

	/**
	 * @return String
	 */
	String getDownloadURL();

	/**
	 * @return int
	 */
	int getDownloadsCount();

	/**
	 * @return String
	 */
	String getFactorioVersion();

	/**
	 * The file name of the release. Always seems to follow the pattern "{name}_{version}.zip"
	 * @return String
	 */
	String getFileName();

	/**
	 * @return int
	 */
	int getFileSize();

	/**
	 * @return String
	 */
	String getGameVersion();

	/**
	 * A numerical ID unique to this release.
	 * @return int
	 */
	int getID();

	/**
	 * A copy of the mod's info.json file.
	 * @return String
	 */
	//TODO
	String getInfoJson();

	/**
	 * @return String
	 */
	String getReleasedAt();

	/**
	 * The version string of this mod release. Used to determine dependencies.
	 * @return String
	 */
	String getVersion();
}
