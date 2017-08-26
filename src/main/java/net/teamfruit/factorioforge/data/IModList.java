package net.teamfruit.factorioforge.data;

import java.util.List;

public interface IModList {

	/**
	 * Information on ModList page.
	 * @return IPagination
	 */
	IPagination getPagination();

	/**
	 * A list of mods, matching any filters you specified.
	 * @return List<IResult>
	 */
	List<IResult> getResults();

}
