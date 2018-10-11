package org.mule.burster.audit;

import org.mule.burster.TaskDescription;
import org.mule.burster.TaskId;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class BursterConsole implements BurstListener {

	private ArrayList<CompletableFuture> completableFutures = new ArrayList<>();

	public long getRunningSize() {
		return completableFutures.stream().filter(t -> !t.isDone()).count();
	}

	public void append(CompletableFuture<?> rCompletableFuture) {
		completableFutures.add(rCompletableFuture);
	}

	@Override
	public void taskAppened(TaskDescription description) {

	}

	@Override
	public void taskFinished(TaskId id) {

	}

	@Override
	public void taskStarted(TaskId id) {

	}
}
