package net.teamfruit.factorioforge.factorioapi.data.modportal;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.IResponse;
import net.teamfruit.factorioforge.factorioapi.ModListAPI;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Result;

public interface IModList extends IResponse {

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

	boolean hasNext();

	ModListAPI next();
}
