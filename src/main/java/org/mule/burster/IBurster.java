package org.mule.burster;

import org.mule.burster.audit.BurstListener;
import org.mule.burster.audit.BursterConsole;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface IBurster<OUT> {


    static <R> Burster<R> execute(Supplier<? super R> rRunnable) {
        return new Burster<>(rRunnable);
    }

    static Burster run(Runnable rRunnable) {
        return new Burster<>(() -> {
            rRunnable.run();
            return null;
        });
    }

    static <R> Burster<R> execute(BurstListener listener, Supplier<R> supplier) {

        return new Burster<R>(listener , supplier);
    }

    static <R> MultiBurster<R> execute(Supplier<R>... rRunnables) {

        return new MultiBurster<R>(rRunnables);
    }

    static <R> MultiBurster<R> executeAll(BurstListener listener, Supplier<R>... rRunnables) {

        return new MultiBurster<R>(listener , rRunnables);
    }

    static Burster run(BurstListener bursterConsole, Runnable rRunnable) {
        return new Burster<>(bursterConsole, () -> {
            rRunnable.run();
            return null;
        });
    }

    /**
     * Blocking function waiting for all dependencies to be burned to return
     *
     * @return The result after computation
     */
    OUT get() throws ExecutionException, InterruptedException;

    <R> IBurster<R> map(Function<? super OUT, R> function);

    default <R> IBurster<R> collect(Collector<? super OUT, ?, R> o) throws ExecutionException, InterruptedException {
        return map(Stream::of).map(t -> t.collect(o));
    }

}
