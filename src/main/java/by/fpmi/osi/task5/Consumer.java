package by.fpmi.osi.task5;

import java.util.List;
import java.util.Queue;

public class Consumer implements Runnable {
    private final Queue<List<Integer>> resource;
    private boolean run = true;
    public Consumer(Queue<List<Integer>> resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (run) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (resource) {
            if (resource.isEmpty()) {
                resource.wait();
            }
            if(!run){
                return;
            }
            List<Integer> peek = resource.poll();
            int sum = peek.stream().reduce(0, Integer::sum);
            double avg = sum * 1. / peek.size();
            System.out.println("Avg of list" + peek + " = " + avg);
            resource.notifyAll();
        }
    }

    public void stop() {
        run = false;
    }
}
