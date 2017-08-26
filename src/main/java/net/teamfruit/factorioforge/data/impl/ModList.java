package net.teamfruit.factorioforge.data.impl;

import java.util.List;

import net.teamfruit.factorioforge.data.IModList;

public class ModList implements IModList {

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

}
