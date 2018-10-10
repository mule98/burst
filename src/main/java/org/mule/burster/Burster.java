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

	Burster(Supplier<OUT> rRunnable) {
		future = executeNow(rRunnable);
	}

	static private <R> CompletableFuture<R> executeNow(Supplier<R> rRunnable) {
		System.out.println("Executing " + rRunnable);
		if (Burster.executorService.isShutdown())
			throw new RuntimeException("Executor service is shutdown");
		final CompletableFuture<R> rCompletableFuture =
				CompletableFuture.supplyAsync(rRunnable, Burster.executorService);
		BursterLogger.append(rCompletableFuture);
		return rCompletableFuture;
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
