package fr.jfosse.demo._2_callable;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;

public class DemoCallable {

    public static void main(String[] args) {
        var callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                var start = Instant.now().toEpochMilli();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var end = Instant.now().toEpochMilli();
                System.out.println("Callable executed in " + (end - start) + " ms.");
                return RandomGenerator.getDefault().nextInt(1, 100);
            }
        };

        try (var executor = Executors.newSingleThreadExecutor()) {
            executor.submit(callable);
        }
    }
}
