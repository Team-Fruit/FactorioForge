package net.teamfruit.factorioforge.factorioapi;

import net.teamfruit.factorioforge.factorioapi.data.IResult;
import net.teamfruit.factorioforge.factorioapi.data.impl.Result;

public class ModSearchAPI extends AbstractAPIRequest<IResult> {

	protected String modName;

	public ModSearchAPI(final String modName) {
		super("mods/");
		this.modName = modName;
	}

	@Override
	public String getURL() {
		return super.getURL()+this.modName;
	}

	@Override
	protected IResult parseAPIResponse(final String raw) {
		return FactorioAPI.gson.fromJson(raw, Result.class);
	}
}
