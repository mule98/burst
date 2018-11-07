package org.mule.burster;

import org.mule.burster.audit.BurstListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

class Capsule<T> {

    private final TaskId id = TaskId.create();
    private final BurstListener listener;
    private final CompletableFuture<T> future;

    Capsule(Supplier<CompletableFuture<T>> startingJob, BurstListener listener) {
        TaskDescription describe = this.describe();
        listener.taskAppended(describe);
        this.future = startingJob.get();
        listener.taskStarted(this.describe());
        this.listener = listener;
        this.future.whenCompleteAsync((result, exception) -> listener.taskFinished(this.describe()));
    }

    private TaskDescription describe() {
        return new TaskDescription(id);
    }

    T get() {
        try {
            System.out.println("Reading " + id);
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Exception while executing " + id, e);
        }
    }
}
