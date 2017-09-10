package net.teamfruit.factorioforge.factorioapi.data.modportal;

public interface ITag {

	/**
	 * 	A numerical ID unique to this tag.
	 * @return int
	 */
	int getID();

	/**
	 * An all lower-case string used to identify this tag internally.
	 * @return String
	 */
	String getName();

	/**
	 * The tag's human-readable tag name.
	 * @return String
	 */
	String getTitle();

	/**
	 * A short description for the tag.
	 * @return String
	 */
	String getDescription();

	/**
	 * @return String
	 */
	String getType();
}
