package net.teamfruit.factorioforge.mod;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.FactorioAPIException;
import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.IShortResult;

public class RepositoryManager {
	public static final RepositoryManager INSTANCE = new RepositoryManager();

	public final ExecutorService executor = Executors.newFixedThreadPool(2, (r) -> new Thread(r, "FactorioForge-communication-thread"));
	private IModList modList;
	private final Deque<Consumer<Optional<IModList>>> thenAccepts = new ArrayDeque<>();
	private final Map<Integer, IShortResult> results = new HashMap<>();
	private boolean error;
	private Throwable throwable;
	private int count;
	private int pageCount;

	private RepositoryManager() {
	}

	public void init() {
		requestModList(1, 1).whenComplete((result1, t1) -> {
			this.count = result1.getPagination().getCount();
			this.pageCount = result1.getPagination().getPageCount();
			final CompletableFuture<IModList> future = requestModList(1, this.count);
			future.whenComplete((result2, t) -> {
				if (t!=null||result2.isError()) {
					this.error = true;
					if (t!=null)
						this.throwable = t;
					else
						this.throwable = new FactorioAPIException(result2.getErrorMessage());
				} else {
					this.modList = result2;
					result2.getResults().stream().forEach(r -> this.results.put(r.getId(), r));
				}
			});
			future.thenAccept(result2 -> this.thenAccepts.stream().forEach(c -> c.accept(Optional.ofNullable(result2))));
		});
	}

	public boolean isComplete() {
		return this.modList!=null;
	}

	public boolean isError() {
		return this.error;
	}

	public IModList getModList() {
		return this.modList;
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

	public Optional<IShortResult> getResultById(final int id) {
		return Optional.ofNullable(this.results.get(id));
	}

	public void thenAccept(final Consumer<Optional<IModList>> consumer) {
		if (isComplete())
			consumer.accept(Optional.ofNullable(this.modList));
		this.thenAccepts.offer(consumer);
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
