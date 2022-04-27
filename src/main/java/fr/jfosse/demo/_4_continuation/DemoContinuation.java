package fr.jfosse.demo._4_continuation;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class DemoContinuation {
    public static void main(String[] args) {
        var scope = new ContinuationScope("My Scope");
        var continuation = new Continuation(scope, () -> {
            System.out.println("Step 1");
            Continuation.yield(scope);
            System.out.println("Step 2");
            Continuation.yield(scope);
            System.out.println("Step 3");
            Continuation.yield(scope);
            System.out.println("Step 4");
            Continuation.yield(scope);
        });

        System.out.println("Start");
        continuation.run();
        System.out.println("Back");
        continuation.run();
        System.out.println("Back again");
        continuation.run();
        System.out.println("Back again and again");
        continuation.run();
        System.out.println("end");
    }
}
