package fr.jfosse.demo._3_virtual_threads;

import java.time.Instant;
import java.util.stream.IntStream;

public class DemoHowManyVirtualThreads {
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

        var vthreads = IntStream.range(0, 1_000_000)
            .mapToObj(i -> Thread.ofVirtual().name("vthread " + i).unstarted(new MyRunnable(i)))
            .toList();

        var counter = 0;

        for (Thread thread : vthreads) {
            try {
                thread.start();
                counter++;
                System.out.println(counter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        for (Thread thread : vthreads) {
            thread.join();
        }
    }
}
