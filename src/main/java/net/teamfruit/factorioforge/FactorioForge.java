package net.teamfruit.factorioforge;

import java.io.File;

import net.teamfruit.factorioforge.ui.UI;

public class FactorioForge {

	public static FactorioForge instance = new FactorioForge();

	public final File workingDir = OperatingSystem.getCurrentPlatform().getWorkingDirectory(".factorioforge");
	public final File factorioDir = OperatingSystem.getCurrentPlatform().getWorkingDirectory("Factorio");

	public static void main(final String[] args) {
		instance.launch();
	}

	public void launch() {
		if (!this.workingDir.exists())
			this.workingDir.mkdirs();

		RepositoryManager.instance.init();
		RepositoryManager.instance.thenAccept(System.out::println);

		UI.launchApplication();
	}
}
