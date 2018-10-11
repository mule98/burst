package org.mule.burster;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class BursterExecutor {

    final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public BursterExecutor() {
    }

    <R> Capsule<R> executeNow(Supplier<R> rRunnable) {
        System.out.println("Executing " + rRunnable);
        if (executorService.isShutdown()) throw new RuntimeException("Executor service is shutdown");
        final CompletableFuture<R> rCompletableFuture = CompletableFuture.supplyAsync(rRunnable, executorService);
        return new Capsule<>(rCompletableFuture);
    }


}