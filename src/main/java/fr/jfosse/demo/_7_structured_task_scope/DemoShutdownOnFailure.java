package fr.jfosse.demo._7_structured_task_scope;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.random.RandomGenerator;

public class DemoShutdownOnFailure {
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
                int waitingtime = RandomGenerator.getDefault().nextInt(100, 4_000);
                if (waitingtime % 2 == 0) {
                    throw new RuntimeException("Waiting Time can't be even.");
                }
                Thread.sleep(waitingtime);
                System.out.println("Task 2 finished !");
                return 5;
            }
        };

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var future1 = scope.fork(task1);
            var future2 = scope.fork(task2);

            scope.join();
            scope.throwIfFailed(ex -> new RuntimeException("Fail !!!", ex));
            System.out.println("result = " + (future1.get() + future2.get()));
        }
    }
}
