package net.teamfruit.factorioforge.mod;

import java.io.File;
import java.io.IOException;

import net.teamfruit.factorioforge.FactorioForge;

public class ModListManager {
	public static final ModListManager INSTANCE = new ModListManager();

	private ModListManager() {
	}

	private File modListFile = new File(FactorioForge.instance.factorioDir, "mods/mod-list.json");
	private ModListBean modList;

	public ModListBean getModList() throws IOException {
		if (this.modList==null)
			this.modList = ModListConverter.getModList(this.modListFile);
		return this.modList;
	}

	public ModListManager save() throws IOException {
		ModListConverter.setModList(this.modListFile, this.modList);
		return this;
	}
}
