package net.teamfruit.factorioforge.factorioapi;

import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.impl.ModList;

public class ModListAPI extends AbstractAPIRequest {

	protected int page;

	public ModListAPI() {
		this(1);
	}

	public ModListAPI(final int page) {
		super("mods");
		this.page = page;
	}

	@Override
	public String getURL() {
		return super.getURL()+"?page="+this.page;
	}

	@Override
	protected IModList parseAPIResponse(final String raw) {
		return FactorioAPI.gson.fromJson(raw, ModList.class);
	}

}
