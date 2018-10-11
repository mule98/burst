package org.mule.burster;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class Capsule<T> {

    private final CompletableFuture<T> future;

    Capsule(CompletableFuture<T> future){
        this.future = future;
    }

    T get() throws ExecutionException, InterruptedException {
        return future.get();
    }
}
