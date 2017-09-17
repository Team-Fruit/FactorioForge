package net.teamfruit.factorioforge.mod;

import java.io.File;
import java.io.IOException;

import net.teamfruit.factorioforge.FactorioForge;
import net.teamfruit.factorioforge.mod.ModListBean.ModBean;

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

	public ModListManager add(final ModBean bean) throws IOException {
		getModList().mods.add(bean);
		return this;
	}

	public ModListManager save() throws IOException {
		if (this.modList!=null)
			ModListConverter.setModList(this.modListFile, this.modList);
		return this;
	}
}
