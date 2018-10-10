package org.mule.burster;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class MappingBurster<F, R> implements IBurster<R> {

	private final Burster<R> burster;

	MappingBurster(Burster<F> outBurster, Function<F, R> function) {
		burster = Burster.execute(() -> {
			try {
				return function.apply(outBurster.get());
			} catch (ExecutionException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

	}

	@Override
	public R get() throws ExecutionException, InterruptedException {
		return burster.get();
	}

	@Override
	public <R1> IBurster<R1> map(Function<R, R1> function) {
		return burster.map(function);
	}
}
