package net.teamfruit.factorioforge.factorioapi.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.IPagination;

public class Pagination implements IPagination {

	private int count;
	private PaginationLink links;
	private int page;
	private int page_count;
	private int page_size;

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public PaginationLink getLinks() {
		return this.links;
	}

	@Override
	public int getPage() {
		return this.page;
	}

	@Override
	public int getPageCount() {
		return this.page_count;
	}

	@Override
	public int getPageSize() {
		return this.page_size;
	}

}
