package by.fpmi.osi.task5;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.List;

public class Main {

    private static final int DELAY_SECONDS = 2;
    public static final int ITERATIONS = 3;
    public static final int PRODUCERS_COUNT = 1;


    public static void main(String[] args)  {

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            ExecutorService service = Executors.newFixedThreadPool(2);
            Queue<List<Integer>>resource = new ArrayDeque<>();
            Consumer consumer = new Consumer(resource);
            Producer producer = new Producer(resource, ITERATIONS, DELAY_SECONDS, Collections.singletonList(consumer));
            service.execute(consumer);
            service.execute(producer);
            service.shutdown();
        }

    }
}
