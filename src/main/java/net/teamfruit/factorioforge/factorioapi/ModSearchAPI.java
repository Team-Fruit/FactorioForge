package net.teamfruit.factorioforge.factorioapi;

import com.google.gson.stream.JsonReader;

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
	protected IResult parseAPIResponse(final JsonReader jr) {
		return FactorioAPI.gson.fromJson(jr, Result.class);
	}
}
