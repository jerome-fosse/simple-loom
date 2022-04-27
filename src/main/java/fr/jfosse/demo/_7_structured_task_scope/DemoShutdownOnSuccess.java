package fr.jfosse.demo._7_structured_task_scope;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.random.RandomGenerator;

/**
 run with the following flags : --enable-preview --add-modules jdk.incubator.concurrent
 */
public class DemoShutdownOnSuccess {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var task1 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(RandomGenerator.getDefault().nextInt(1_000, 3_000));
                System.out.println("Task 1 finished !");
                return 2;
            }
        };

        var task2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(RandomGenerator.getDefault().nextInt(100, 4_000));
                System.out.println("Task 2 finished !");
                return 5;
            }
        };

        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Integer>()) {
            var future1 = scope.fork(task1);
            var future2 = scope.fork(task2);
            try {
                scope.joinUntil(Instant.now().plus(2_000, ChronoUnit.MILLIS));
            } catch (TimeoutException e) {
                System.out.println("Both threads lasted more than 2 secondes.");
            }
            System.out.println("result = " + scope.result());
        }
    }
}
