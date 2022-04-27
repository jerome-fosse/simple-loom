package fr.jfosse.demo._1_runnable;

import java.time.Instant;

public class DemoRunnable {

    public static void main(String[] args) throws InterruptedException {
        var runnable = new Runnable() {
            @Override
            public void run() {
                var start = Instant.now().toEpochMilli();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var end = Instant.now().toEpochMilli();
                System.out.println("Runnable executed in " + (end - start) + " ms.");
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        thread.join();
        System.out.println("End.");
    }
}
