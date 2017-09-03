package net.teamfruit.factorioforge.factorioapi.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.IPaginationLink;

public class PaginationLink implements IPaginationLink {

	private String first;
	private String prev;
	private String next;
	private String last;

	@Override
	public String getFirst() {
		return this.first;
	}

	@Override
	public String getPrev() {
		return this.prev;
	}

	@Override
	public String getNext() {
		return this.next;
	}

	@Override
	public String getLast() {
		return this.last;
	}

	@Override
	public boolean hasNext() {
		return getNext()!=null;
	}

}
