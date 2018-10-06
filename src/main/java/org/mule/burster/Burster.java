package org.mule.burster;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Burster {

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public <T> Future<T> append(Ticket<T> ticket) {
        return executorService.submit(ticket::burn);
    }
}
