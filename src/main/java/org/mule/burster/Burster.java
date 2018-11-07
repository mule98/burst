package org.mule.burster;

import org.mule.burster.audit.BurstListener;
import org.mule.burster.audit.BursterConsole;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Burster<OUT> implements IBurster<OUT> {

    private BurstListener console = new BursterConsole();
    private final BursterExecutor bursterExecutor = new BursterExecutor();

    private Capsule<? super OUT> future;

    Burster(Supplier<? super OUT> rRunnable) {
        future = bursterExecutor.executeNow(rRunnable, console);
    }

    public Burster(BurstListener listener, Supplier<OUT> supplier) {
        console = listener;
        future = bursterExecutor.executeNow(supplier, console);
    }


    @Override
    public OUT get() throws ExecutionException, InterruptedException {
        return (OUT) future.get();
    }

    @Override
    public <R> IBurster<R> map(Function<? super OUT, R> function) {
        return new MappingBurster<>(this, function);
    }

    @Override
    public <R> IBurster<R> collect(Collector<? super OUT, ?, R> o) throws ExecutionException, InterruptedException {

        return map(Stream::of).map(t -> t.collect(o));
    }

}
