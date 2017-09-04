package net.teamfruit.factorioforge.factorioapi.data;

import net.teamfruit.factorioforge.factorioapi.ModListAPI;

public interface IPagination {

	/**
	 * Total number of mods that match your specified filters.
	 * @return int
	 */
	int getCount();

	/**
	 * 	Utility links to mod portal api requests, preserving all filters and search queries.
	 * @return IPaginationLinks
	 */
	IPaginationLink getLinks();

	/**
	 * The current page number.
	 * @return int
	 */
	int getPage();

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

	boolean hasNext();

	ModListAPI next();

}
