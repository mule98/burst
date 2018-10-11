import org.junit.jupiter.api.Test;
import org.mule.burster.Burster;
import org.mule.burster.IBurster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BursterTest {

	@Test
	void createBurster() {
		IBurster.execute(() -> null);
	}

	@Test
	void burstOneJob() throws InterruptedException, ExecutionException {

		String expected = "Toto";
		final String s = IBurster.execute(() -> expected).get();


		assertEquals(expected, s);
	}


	@Test
	void burstFeedDatas() throws InterruptedException, ExecutionException {

		String expected = "Toto";
		final String s = IBurster.execute(() -> expected).get();


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
			System.out.println(Thread.currentThread().getName() + " executing " + uuid);
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (uuid) {Thread.sleep(1000);}
			System.out.println(Thread.currentThread().getName() + " finished " + uuid);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void recursiveBurst() throws ExecutionException, InterruptedException {

		String expected = "recursiveResult";


		final String s = IBurster.execute(() -> 1L).map(Function.identity()).map((t) -> t + expected).get();

		assertEquals("1recursiveResult", s);

	}

	@Test
	void logBurstStatusses() throws InterruptedException {

		String expected = "recursiveResult";
		Semaphore semaphore = new Semaphore(1);
		semaphore.acquire();


        Burster burster = IBurster.run(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(1, burster.console().getRunningSize());
		assertEquals(0, burster.console().getQueuedSize());
		assertEquals(0, burster.console().getFinishedSize());
		semaphore.release();
		wait1Sec();
		assertEquals(0, burster.console().getRunningSize());
		assertEquals(0, burster.console().getQueuedSize());
		assertEquals(1, burster.console().getFinishedSize());

	}
}
