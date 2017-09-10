package net.teamfruit.factorioforge.factorioapi.data.modportal;

public interface IPaginationLink {

	/**
	 * URL to the first page of the results, or null if you're already on the first page.
	 * @return String
	 */
	String getFirst();

	/**
	 * URL to the previous page of the results, or null if you're already on the first page.
	 * @return String
	 */
	String getPrev();

	/**
	 * URL to the next page of the results, or null if you're already on the last page.
	 * @return String
	 */
	String getNext();

	/**
	 * URL to the last page of the results, or null if you're already on the last page.
	 * @return String
	 */
	String getLast();

	boolean hasNext();

}
