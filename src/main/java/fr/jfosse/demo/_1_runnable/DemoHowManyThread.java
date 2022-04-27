package fr.jfosse.demo._1_runnable;

import java.time.Instant;
import java.util.stream.IntStream;

public class DemoHowManyThread {

    public static void main(String[] args) throws InterruptedException {
        class MyRunnable implements Runnable {
            private int id;

            public MyRunnable(int id) {
                this.id = id;
            }

            @Override
            public void run() {
                var start = Instant.now().toEpochMilli();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var end = Instant.now().toEpochMilli();
                //System.out.println("Runnable " + id + " executed in " + (end - start) + " ms.");
            }
        }

        var threads = IntStream.range(0, 100_000)
            .mapToObj(i -> new Thread(new MyRunnable(i)))
            .toList();

        var counter = 0;

        for (Thread thread : threads) {
            try {
                thread.start();
                counter++;
                System.out.println(counter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
