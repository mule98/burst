package org.mule.burster;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public interface IBurster<OUT> {

	OUT get() throws ExecutionException, InterruptedException;

	<R> IBurster<R> map(Function<OUT, R> function);
}
