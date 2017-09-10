package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.ModList;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Pagination;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Result;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IModList;

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
	protected IModList parseAPIResponse(final JsonReader jr) throws IOException {
		final ModList modList = new ModList();
		modList.setResults(new ArrayList<>());
		jr.beginObject();
		while (jr.hasNext()) {
			try {
				switch (jr.nextName()) {
					case "pagination":
						modList.setPagination(FactorioAPI.gson.fromJson(jr, Pagination.class));
						break;
					case "results":
						jr.beginArray();
						while (jr.hasNext())
							modList.getResults().add(FactorioAPI.gson.fromJson(jr, Result.class));
						jr.endArray();
						break;
				}
			} catch (final Exception e) {
				Log.log.warn("Failed parsing mod information");
			}
		}
		jr.endObject();
		return modList;
	}

}
