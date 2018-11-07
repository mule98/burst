package org.mule.burster;

import org.mule.burster.audit.BurstListener;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiBurster<Z> implements IBurster<Z> {

    private final List<IBurster<Z>> lonelyBursters;

    public MultiBurster(Supplier<Z>[] rRunnables) {
        this(Stream.of(rRunnables).map(IBurster::execute));
    }


    public MultiBurster(Stream<IBurster<Z>> rStream) {
        lonelyBursters = rStream.collect(Collectors.toList());
    }

    public MultiBurster(BurstListener listener, Supplier<Z>[] rRunnables) {
        lonelyBursters = Stream.of(rRunnables).map(t -> IBurster.execute(listener, t)).collect(Collectors.toList());
    }

    @Override
    public Z get() {
        return lonelyBursters.stream()
                             .map(getiBursterZFunction())
                             .findFirst()
                             .orElseThrow(() -> new RuntimeException("Cannot get value from result"));
    }

    private Function<IBurster<? super Z>, Z> getiBursterZFunction() {
        return iBurster -> {
            try {
                return (Z) iBurster.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public <R> IBurster<R> map(Function<? super Z, R> function) {
        Stream<IBurster<Z>> stream = lonelyBursters.stream();
        Stream<IBurster<R>> rStream = stream.map(burster -> burster.map(function));
        return new MultiBurster<>(rStream);
    }
}
