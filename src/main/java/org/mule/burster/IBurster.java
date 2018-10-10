package org.mule.burster;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IBurster<OUT> {


	static <R> Burster<R> execute(Supplier<R> rRunnable) {
		return new Burster<>(rRunnable);
	}

	static void run(Runnable rRunnable) {
		new Burster<>(() -> {
			rRunnable.run();
			return null;
		});
	}

	OUT get() throws ExecutionException, InterruptedException;

	<R> IBurster<R> map(Function<OUT, R> function);
}
