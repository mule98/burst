package org.mule.burster;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class Burster<OUT> implements IBurster<OUT> {

	private final static ExecutorService executorService = Executors.newFixedThreadPool(20);

	private CompletableFuture<OUT> future;

	private Burster(Supplier<OUT> rRunnable) {
		future = executeNow(rRunnable);
	}

	private static <R> CompletableFuture<R> executeNow(Supplier<R> rRunnable) {
		System.out.println("Executing " + rRunnable);
		if (executorService.isShutdown())
			throw new RuntimeException("Executor service is shutdown");
		return CompletableFuture.supplyAsync(rRunnable, executorService);
	}

	public static <R> Burster<R> execute(Supplier<R> rRunnable) {
		return new Burster<>(rRunnable);
	}

	@Override
	public OUT get() throws ExecutionException, InterruptedException {
		return future.get();
	}

	@Override
	public <R> IBurster<R> map(Function<OUT, R> function) {
		return new MappingBurster<>(this, function);
	}

}
