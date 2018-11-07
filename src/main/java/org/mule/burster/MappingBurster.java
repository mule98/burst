package org.mule.burster;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collector;

public class MappingBurster<F, R> implements IBurster<R> {

	private final Burster<R> burster;

	MappingBurster(IBurster<F> outBurster, Function<? super F, R> function) {
		burster = new Burster<>(() -> {
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
	public <R1> IBurster<R1> map(Function< ? super R, R1> function) {
		return burster.map(function);
	}

    @Override
    public <R1> IBurster<R1> collect(Collector<?super R, ?, R1> o) throws ExecutionException, InterruptedException {
        return burster.collect(o);
    }
}
