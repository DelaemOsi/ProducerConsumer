package by.fpmi.osi.task5;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private final Queue<List<Integer>> resource;
    private final int iterations;
    private final List<Consumer> consumers;
    private final int delaySeconds;

    public Producer(Queue<List<Integer>> resource, int iterations, int delaySeconds, List<Consumer>consumers) {
        this.consumers = consumers;
        this.resource = resource;
        this.iterations = iterations;
        this.delaySeconds = delaySeconds;
    }

    public void stopConsumers() {
        for (Consumer consumer : consumers) {
            consumer.stop();
        }
    }

    @Override
    public void run() {
        synchronized (resource) {
            for (int i = 0; i < iterations; i++) {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopConsumers();
            resource.notifyAll();
        }
    }

    private void produce() throws InterruptedException {
        List<Integer> randomList = new ArrayList<>();
        Random random = new Random();
        int randomSize = random.nextInt(3) + 1;
        for (int i = 0; i < randomSize; i++) {
            int randomNumber = random.nextInt(100);
            randomList.add(randomNumber);
        }
        resource.add(randomList);
        System.out.println("Generated list " + randomList);
        resource.notifyAll();
        resource.wait();
        TimeUnit.SECONDS.sleep(delaySeconds);
    }
}
