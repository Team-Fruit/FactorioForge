package net.teamfruit.factorioforge;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.IModList;

public class RepositoryManager {
	public static RepositoryManager instance = new RepositoryManager();

	public final ExecutorService executor = Executors.newSingleThreadExecutor((r) -> new Thread(r, "FactorioForge-communication-thread"));
	private final Map<Integer, IModList> modlists = new HashMap<>();

	private RepositoryManager() {
	}

	public void init() {
		requestModList(1).whenComplete((result, t) -> this.modlists.put(1, result));
	}

	public boolean contains(final int page) {
		return this.modlists.containsKey(page);
	}

	public CompletableFuture<IModList> requestModList(final int page) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return FactorioAPI.getModList(page);
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
		}, this.executor);
	}
}
