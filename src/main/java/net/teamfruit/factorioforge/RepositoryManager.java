package net.teamfruit.factorioforge;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.IRelease;

public class RepositoryManager {
	public static RepositoryManager instance = new RepositoryManager();

	public final ExecutorService executor = Executors.newFixedThreadPool(2, (r) -> new Thread(r, "FactorioForge-communication-thread"));
	private final List<IRelease> releases = new ArrayList<>();
	private final Deque<Consumer<IModList>> thenAccepts = new ArrayDeque<>();
	private int count;
	private int pageCount;

	private RepositoryManager() {
	}

	public void init() {
		requestModList(1, 1).whenComplete((result1, t1) -> {
			this.count = result1.getPagination().getCount();
			this.pageCount = result1.getPagination().getPageCount();
			final CompletableFuture<IModList> future = requestModList(1, this.count);
			future.whenComplete((result2, t2) -> result2.getResults().stream().forEach(r -> this.releases.add(r.getShortResult().getLatestRelease())));
			future.thenAccept(result2 -> this.thenAccepts.stream().forEach(c -> c.accept(result2)));
		});
	}

	public boolean isComplete() {
		return this.releases.size()>=this.pageCount;
	}

	public List<IRelease> getReleases() {
		return this.releases;
	}

	public int getCount() {
		if (!isComplete())
			return -1;
		return this.count;
	}

	public int getPageCount() {
		if (!isComplete())
			return -1;
		return this.pageCount;
	}

	public void thenAccept(final Consumer<IModList> consumer) {
		this.thenAccepts.offer(consumer);
	}

	public Optional<IRelease> getReleaseById(final int id) {
		return this.releases.stream().filter(r -> r.getID()==id).findAny();
	}

	private CompletableFuture<IModList> requestModList(final int page, final int pageSize) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				final IModList modList = FactorioAPI.getModList(page, pageSize);
				return modList;
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
		}, this.executor);
	}

}
