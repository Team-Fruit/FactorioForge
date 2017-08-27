package net.teamfruit.factorioforge.factorioapi.data;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.data.impl.Result;

public interface IModList extends IError {

	/**
	 * Information on ModList page.
	 * @return IPagination
	 */
	IPagination getPagination();

	/**
	 * A list of mods, matching any filters you specified.
	 * @return List<IResult>
	 */
	List<Result> getResults();

}
