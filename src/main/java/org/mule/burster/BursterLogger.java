package org.mule.burster;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class BursterLogger {

	private static ArrayList<CompletableFuture> completableFutures = new ArrayList<>();

	public static long getRunningSize() {
		return completableFutures.stream().filter(t -> !t.isDone()).count();
	}

	public static void append(CompletableFuture<?> rCompletableFuture) {
		completableFutures.add(rCompletableFuture);
	}
}
