package fr.jfosse.demo._7_structured_task_scope;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 run with the following flags : --enable-preview --add-modules jdk.incubator.concurrent
 */
public class DemoStructuredTaskScope {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var task1 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2_000);
                System.out.println("Task 1 finished !");
                return 2;
            }
        };

        var task2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(4_000);
                System.out.println("Task 2 finished !");
                return 5;
            }
        };

        try (var scope = new StructuredTaskScope<Integer>()) {
            var future1 = scope.fork(task1);
            var future2 = scope.fork(task2);
            scope.join();
            System.out.println("result = " + (future1.get() + future2.get()));
        }
    }
}
