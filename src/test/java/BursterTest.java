import org.junit.jupiter.api.Test;
import org.mule.burster.Burster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BursterTest {

	@Test
	void createBurster() {
		Burster.execute(() -> null);
	}

	@Test
	void burstOneJob() throws InterruptedException, ExecutionException {

		String expected = "Toto";
		final String s = Burster.execute(() -> expected).get();


		assertEquals(expected, s);
	}


	@Test
	void burstFeedDatas() throws InterruptedException, ExecutionException {

		String expected = "Toto";
		final String s = Burster.execute(() -> expected).get();


		assertEquals(expected, s);
	}

	@Test
	void burstParllelJobs() {

		String expected = "Toto";

		List<Burster> bursters = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			bursters.add(Burster.execute(() -> {
				try {
					final UUID uuid = UUID.randomUUID();
					System.out.println(Thread.currentThread().getName() + " executing " + uuid);
					//noinspection SynchronizationOnLocalVariableOrMethodParameter
					synchronized (uuid) {Thread.sleep(1000);}
					System.out.println(Thread.currentThread().getName() + " finished " + uuid);
					return expected;
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
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

	@Test
	void recursiveBurst() throws ExecutionException, InterruptedException {

		String expected = "recursiveResult";


		final String s = Burster.execute(() -> 1L).map(Function.identity()).map((t) -> t + expected).get();

		assertEquals("1recursiveResult", s);

	}
	//TODO: log statuses
}
