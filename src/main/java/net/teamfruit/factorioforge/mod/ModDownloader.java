package net.teamfruit.factorioforge.mod;

import javafx.concurrent.Task;

public class ModDownloader extends Task<Void> {

	private static String token;

	public static void setToken(final String token) {
		ModDownloader.token = token;
	}

	public static boolean isTokenProvided() {
		return ModDownloader.token!=null;
	}

	@Override
	protected Void call() throws Exception {
		return null;
	}

}
