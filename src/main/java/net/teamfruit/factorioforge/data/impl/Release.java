package net.teamfruit.factorioforge.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.IRelease;

public class Release implements IRelease {

	private String download_url;
	private int downloads_count;
	private String factorio_version;
	private String file_name;
	private int file_size;
	private String game_version;
	private int id;
	//TODO
	private String info_json;
	private String released_at;
	private String version;

	@Override
	public String getDownloadURL() {
		return this.download_url;
	}

	@Override
	public int getDownloadsCount() {
		return this.downloads_count;
	}

	@Override
	public String getFactorioVersion() {
		return this.factorio_version;
	}

	@Override
	public String getFileName() {
		return this.file_name;
	}

	@Override
	public int getFileSize() {
		return this.file_size;
	}

	@Override
	public String getGameVersion() {
		return this.game_version;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getInfoJson() {
		return this.info_json;
	}

	@Override
	public String getReleasedAt() {
		return this.released_at;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

}
