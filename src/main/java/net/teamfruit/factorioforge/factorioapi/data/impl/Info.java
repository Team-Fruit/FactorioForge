package net.teamfruit.factorioforge.factorioapi.data.impl;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.data.IInfo;

public class Info implements IInfo {

	private String author;
	private String contact;
	private List<String> dependencies;
	private String description;
	private String factorio_version;
	private String homepage;
	private String name;
	private String title;
	private String version;

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public String getContact() {
		return this.contact;
	}

	@Override
	public List<String> getDependencies() {
		return this.dependencies;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getFactorioVersion() {
		return this.factorio_version;
	}

	@Override
	public String getHomePage() {
		return this.homepage;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

}
