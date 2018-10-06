import org.junit.jupiter.api.Test;
import org.mule.burster.Burned;
import org.mule.burster.Burster;
import org.mule.burster.Ticket;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BursterTest {

    @Test
    void createBurster() {
        Burster burster = new Burster();
    }

    @Test
    void burstOneJob() throws InterruptedException, ExecutionException, TimeoutException {
        Burster burster = new Burster();

        String expected = "Toto";
        Future<String> futureResult = burster.append(() -> expected);


        String result = futureResult.get(1, TimeUnit.SECONDS);
        assertFalse(futureResult.isCancelled());
        assertTrue(futureResult.isDone());
        assertEquals(expected, result);
    }

    @Test
    void recursiveBurst(){
        Burster burster = new Burster();

        String expected = "recursiveResult";


        Ticket<Long> objectTicket = () -> 1L;
        Ticket<String> value = objectTicket.append((t)-> t + expected );
        burster.append(objectTicket);

    }
    //TODO: log statuses
}
