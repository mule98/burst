package org.mule.burster;

import org.mule.burster.audit.BursterConsole;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public class Burster<OUT> implements IBurster<OUT> {

	private final BursterConsole console = new BursterConsole();
	private final BursterExecutor bursterExecutor = new BursterExecutor();

	private Capsule<OUT> future;

	Burster(Supplier<OUT> rRunnable) {
		future = bursterExecutor.executeNow(rRunnable, console);
	}


	@Override
	public OUT get() throws ExecutionException, InterruptedException {
		return future.get();
	}

	@Override
	public <R> IBurster<R> map(Function<OUT, R> function) {
		return new MappingBurster<>(this, function);
	}

	public BursterConsole console() {
		return console;
	}
}
