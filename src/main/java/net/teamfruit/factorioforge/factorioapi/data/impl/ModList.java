package net.teamfruit.factorioforge.factorioapi.data.impl;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.ModListAPI;
import net.teamfruit.factorioforge.factorioapi.data.IModList;

public class ModList extends Response implements IModList {

	private Pagination pagination;
	private List<Result> results;

	@Override
	public Pagination getPagination() {
		return this.pagination;
	}

	@Override
	public List<Result> getResults() {
		return this.results;
	}

	@Override
	public boolean hasNext() {
		return getPagination().hasNext();
	}

	@Override
	public ModListAPI next() {
		return getPagination().next();
	}
}
