package net.teamfruit.factorioforge.factorioapi;

import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.impl.ModList;

public class ModListAPI extends AbstractAPIRequest<IModList> {

	protected int page;
	protected int pageSize;

	public ModListAPI() {
		this(1, 25);
	}

	public ModListAPI(final int page, final int pageSize) {
		super("mods");
		this.page = page;
		this.pageSize = pageSize;
	}

	@Override
	public String getURL() {
		return super.getURL()+"?page="+this.page+"&page_size="+this.pageSize;
	}

	@Override
	protected IModList parseAPIResponse(final String raw) {
		return FactorioAPI.gson.fromJson(raw, ModList.class);
	}

}
