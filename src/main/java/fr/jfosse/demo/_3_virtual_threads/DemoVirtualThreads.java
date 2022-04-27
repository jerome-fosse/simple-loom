package fr.jfosse.demo._3_virtual_threads;

import java.time.Instant;

public class DemoVirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        var thread = Thread.ofPlatform()
            .name("My Platform thread")
            .start(() -> {
                var start = Instant.now().toEpochMilli();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var end = Instant.now().toEpochMilli();
                System.out.println("Platform Thread executed in " + (end - start) + " ms with thread " + Thread.currentThread());
            });

        var vthread = Thread.ofVirtual()
            .name("My virtual thread")
            .start(() -> {
                var start = Instant.now().toEpochMilli();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var end = Instant.now().toEpochMilli();
                System.out.println("Virtual Thread executed in " + (end - start) + " ms with thread " + Thread.currentThread());
            });

        thread.join();
        vthread.join();
    }
}
