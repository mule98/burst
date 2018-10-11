package org.mule.burster;

import org.mule.burster.audit.BurstListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

class Capsule<T> {

	private final TaskId id = TaskId.create();
	private final BurstListener listener;
	private final CompletableFuture<T> future;

	Capsule(Supplier<CompletableFuture<T>>
					startingJob, BurstListener console) {
		console.taskAppended(this.describe());
		this.future = startingJob.get();
		console.taskStarted(this.id);
		this.listener = console;
		this.future.whenCompleteAsync((result, exception) -> console.taskFinished(id));
	}

	private TaskDescription describe() {
		return new TaskDescription(id);
	}

	T get() throws ExecutionException, InterruptedException {
		return future.get();
	}
}
