package net.teamfruit.factorioforge.factorioapi.data.impl.modportal;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.ModListAPI;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IModList;

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

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setResults(List<Result> results) {
		this.results = results;
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
