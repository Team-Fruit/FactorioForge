package net.teamfruit.factorioforge.factorioapi;

import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.impl.ModList;
import net.teamfruit.factorioforge.factorioapi.data.impl.Pagination;
import net.teamfruit.factorioforge.factorioapi.data.impl.Result;

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
	protected IModList parseAPIResponse(final JsonReader jr) {
		ModList modList = new ModList();
		modList.setResults(new ArrayList<>());
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				switch (jr.nextName()) {
					case "pagination":
						Log.log.info("pagination");
						modList.setPagination(FactorioAPI.gson.fromJson(jr, Pagination.class));
						Log.log.info("done");
						break;
					case "results":
						Log.log.info("results");
						jr.beginArray();
						while (jr.hasNext()) {
							Result r = FactorioAPI.gson.fromJson(jr, Result.class);
							Log.log.info(r.getTitle());
							modList.getResults().add(r);
						}
						jr.endArray();
						Log.log.info("done");
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			throw new UncheckedIOException(e);
		}
		return modList;
	}

}
