package net.teamfruit.factorioforge.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.ITag;

public class Tag implements ITag {

	private int id;
	private String name;
	private String title;
	private String description;
	private String type;

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public String getType() {
		return this.type;
	}

}
