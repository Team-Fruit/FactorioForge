package net.teamfruit.factorioforge.factorioapi.data.impl;

import java.util.List;

import net.teamfruit.factorioforge.factorioapi.data.IFullResult;
import net.teamfruit.factorioforge.factorioapi.data.IShortResult;

public class Result extends Response implements IShortResult, IFullResult {

	private String created_at;
	private String current_user_rating;
	private String description;
	private String description_html;
	private int downloads_count;
	private MediaFile first_media_file;
	private List<String> game_versions;
	private String github_path;
	private String homepage;
	private int id;
	private Release latest_release;
	private int license_flags;
	private String license_name;
	private String license_url;
	private List<MediaFile> media_files;
	private String name;
	private String owner;
	private int ratings_count;
	private List<Release> releases;
	private String summary;
	private List<Tag> tags;
	private String title;
	private String updated_at;
	private int visits_count;

	@Override
	public String getCreatedAt() {
		return this.created_at;
	}

	@Override
	public String getCurrentUserRating() {
		return this.current_user_rating;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getDescriptionHTML() {
		return this.description_html;
	}

	@Override
	public int getDownloadsCount() {
		return this.downloads_count;
	}

	@Override
	public MediaFile getFirstMediaFile() {
		return this.first_media_file;
	}

	@Override
	public List<String> getGameVersions() {
		return this.game_versions;
	}

	@Override
	public String getGitHubPath() {
		return this.github_path;
	}

	@Override
	public String getHomepage() {
		return this.homepage;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Release getLatestRelease() {
		return this.latest_release;
	}

	@Override
	public int getLicenseFlags() {
		return this.license_flags;
	}

	@Override
	public String getLicenseName() {
		return this.license_name;
	}

	@Override
	public String getLicenseURL() {
		return this.license_url;
	}

	@Override
	public List<MediaFile> getMediaFiles() {
		return this.media_files;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}

	@Override
	public int getRatingsCount() {
		return this.ratings_count;
	}

	@Override
	public List<Release> getReleases() {
		return this.releases;
	}

	@Override
	public String getSummary() {
		return this.summary;
	}

	@Override
	public List<Tag> getTags() {
		return this.tags;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getUpdatedAt() {
		return this.updated_at;
	}

	@Override
	public int getVisitsCount() {
		return this.visits_count;
	}

	@Override
	public boolean isFullResult() {
		return this.description!=null;
	}

	@Override
	public IShortResult getShortResult() {
		return this;
	}

	@Override
	public IFullResult getFullResult() {
		return this;
	}

}
