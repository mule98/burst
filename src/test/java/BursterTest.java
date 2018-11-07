import org.junit.jupiter.api.Test;
import org.mule.burster.Burster;
import org.mule.burster.IBurster;
import org.mule.burster.audit.BursterConsole;
import org.mule.burster.audit.LogBurstListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BursterTest {

    @Test
    public void aggregateSingleResult() throws ExecutionException, InterruptedException {
        String expected = "Expected result";
        String s = IBurster.execute(() -> expected)
                           .get();

        assertEquals(expected, s);
    }

    @Test
    void createBurster() {
        IBurster.execute(() -> null);
    }

    @Test
    void burstOneJob() throws InterruptedException, ExecutionException {

        String expected = "Toto";
        final String s = IBurster.execute(() -> expected)
                                 .get();


        assertEquals(expected, s);
    }

    @Test
    void burstFeedDatas() throws InterruptedException, ExecutionException {

        String expected = "Toto";
        final String s = IBurster.execute(() -> expected)
                                 .get();


        assertEquals(expected, s);
    }

    @Test
    void burstParllelJobs() {

        String expected = "Toto";

        List<Burster> bursters = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            bursters.add(IBurster.execute(() -> {
                wait1Sec();
                return expected;
            }));
        }
        bursters.forEach(t -> {
            try {
                t.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void wait1Sec() {
        try {
            final UUID uuid = UUID.randomUUID();
            System.out.println(Thread.currentThread()
                                     .getName() + " executing " + uuid);
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (uuid) {
                Thread.sleep(1000);
            }
            System.out.println(Thread.currentThread()
                                     .getName() + " finished " + uuid);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void recursiveBurst() throws ExecutionException, InterruptedException {

        String expected = "recursiveResult";


        final String s = IBurster.execute(() -> 1L)
                                 .map(Function.identity())
                                 .map((t) -> t + expected)
                                 .get();

        assertEquals("1recursiveResult", s);

    }

    @Test
    void logBurstStatusses() throws InterruptedException {

        String expected = "recursiveResult";
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();


        BursterConsole bursterConsole = new BursterConsole();
        Burster burster = IBurster.run(bursterConsole, () -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(1,
                     bursterConsole
                            .getRunningSize());
        assertEquals(0,
                     bursterConsole
                            .getQueuedSize());
        assertEquals(0,
                     bursterConsole
                            .getFinishedSize());
        semaphore.release();
        wait1Sec();
        assertEquals(0,
                     bursterConsole
                            .getRunningSize());
        assertEquals(0,
                     bursterConsole
                            .getQueuedSize());
        assertEquals(1,
                     bursterConsole
                            .getFinishedSize());

    }

    @Test
    void aggregateResult() throws ExecutionException, InterruptedException {
        String value1 = "End message ";
        String value2 = " must contain ";
        String value3 = " all message";

        String result = IBurster.executeAll(new LogBurstListener(), () -> value1, () -> value2, () -> value3)
                                .collect(Collectors.joining(""))
                                .get();

        //Don't ensure execution order as there is no dependency between executors
        assertTrue(result.contains(value1));
        assertTrue(result.contains(value2));
        assertTrue(result.contains(value3));

    }
}
