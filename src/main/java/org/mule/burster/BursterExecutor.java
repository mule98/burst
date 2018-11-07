package org.mule.burster;

import org.mule.burster.audit.BurstListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class BursterExecutor {

    final ExecutorService executorService = Executors.newFixedThreadPool(20);

	<R> Capsule<? super R> executeNow(Supplier<? super R> rRunnable, BurstListener console) {
        System.out.println("Executing " + rRunnable);
        if (executorService.isShutdown()) throw new RuntimeException("Executor service is shutdown");
		return new Capsule<>(() -> CompletableFuture.supplyAsync(rRunnable, executorService), console);
    }


}