package net.teamfruit.factorioforge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UncheckedIOException;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.mod.ModDownloader;
import net.teamfruit.factorioforge.mod.RepositoryManager;
import net.teamfruit.factorioforge.mod.UserData;
import net.teamfruit.factorioforge.ui.UI;

public class FactorioForge {

	public static FactorioForge instance = new FactorioForge();

	public final File workingDir = OperatingSystem.getCurrentPlatform().getWorkingDirectory(".factorioforge");
	public final File factorioDir = OperatingSystem.getCurrentPlatform().getWorkingDirectory("Factorio");
	public final File modsDir = new File(this.factorioDir, "mods");

	public static void main(final String[] args) {
		instance.launch();
	}

	public void launch() {
		if (!this.workingDir.exists())
			this.workingDir.mkdirs();

		RepositoryManager.INSTANCE.init();

		final File file = new File(this.workingDir, "userdata.json");
		if (file.exists())
			try {
				final UserData data = FactorioAPI.gson.fromJson(new FileReader(file), UserData.class);
				ModDownloader.setUser(data);
			} catch (final FileNotFoundException e) {
				throw new UncheckedIOException(e);
			}

		UI.launchApplication();
	}
};