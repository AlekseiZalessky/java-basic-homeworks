package ru.otus.java.basic.homeworks.lesson30;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final Object mon = new Object();
    private static boolean flagA = false;
    private static boolean flagB = false;
    private static boolean flagC = true;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(() -> {
            synchronized (mon) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (!flagC) {
                            mon.wait();
                        }
                        System.out.print("A");
                        flagC = false;
                        flagA = true;
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        service.execute(() -> {
            synchronized (mon) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (!flagA) {
                            mon.wait();
                        }
                        System.out.print("B");
                        flagA = false;
                        flagB = true;
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        service.execute(() -> {
            synchronized (mon) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (!flagB) {
                            mon.wait();
                        }
                        System.out.print("C");
                        flagB = false;
                        flagC = true;
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        service.shutdown();
    }
}
