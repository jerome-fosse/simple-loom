package fr.jfosse.demo._6_ScopeLocal;

import jdk.incubator.concurrent.ScopeLocal;

/**
 run with the following flags : --enable-preview --add-modules jdk.incubator.concurrent
 */
public class DemoScopeLocal {

    private static ScopeLocal<String> scopeLocal = ScopeLocal.newInstance();

    private static void sayHello() {
        System.out.println("Hello " + scopeLocal.orElse("John Doe"));
    }
    public static void main(String[] args) throws InterruptedException {

        var task = new Runnable() {

            @Override
            public void run() {
                ScopeLocal
                    .where(scopeLocal, "Bob")
                    .run(() -> sayHello());
            }
        };

        Thread.ofVirtual().start(task).join();
    }
}
