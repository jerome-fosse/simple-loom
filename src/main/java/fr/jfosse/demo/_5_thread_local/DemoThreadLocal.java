package fr.jfosse.demo._5_thread_local;

public class DemoThreadLocal {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private static void sayHello() {
        System.out.println("Hello " + threadLocal.get() + " !!!");
    }

    public static void main(String[] args) throws InterruptedException {

        var task = new Runnable() {

            @Override
            public void run() {
                threadLocal.set("John");
                sayHello();
            }
        };

        Thread.ofVirtual().start(task).join();
    }
}
