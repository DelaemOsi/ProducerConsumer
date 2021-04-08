package by.fpmi.osi.task5;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.List;

public class Main {

    private static final int DELAY_SECONDS = 2;
    private static final int ITERATIONS = 3;
    private static final int PRODUCERS_COUNT = 13;
    private static final int MAX_CAPACITY = 7;

    public static void main(String[] args)  {

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            ExecutorService service = Executors.newFixedThreadPool(2);
            Queue<List<Integer>>resource = new ArrayDeque<>();
            Consumer consumer = new Consumer(resource);
            Producer producer = new Producer(resource, ITERATIONS, DELAY_SECONDS, consumer, MAX_CAPACITY);
            service.execute(consumer);
            service.execute(producer);
            service.shutdown();
        }

    }
}
