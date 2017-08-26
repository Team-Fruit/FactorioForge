package net.teamfruit.factorioforge.data;

public interface IPagination {

	/**
	 * Total number of mods that match your specified filters.
	 * @return int
	 */
	int getConut();

	/**
	 * 	Utility links to mod portal api requests, preserving all filters and search queries.
	 * @return IPaginationLinks
	 */
	IPaginationLinks getLinks();

	/**
	 * The current page number.
	 * @return int
	 */
	int getPageNumber();

	/**
	 * The total number of pages returned.
	 * @return int
	 */
	int getPageCount();

	/**
	 * The number of results per page.
	 * @return int
	 */
	int getPageSize();

}
