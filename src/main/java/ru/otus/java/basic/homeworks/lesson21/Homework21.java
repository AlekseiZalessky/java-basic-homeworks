package ru.otus.java.basic.homeworks.lesson21;

public class Homework21 {
    public static void main(String[] args) {
        withoutThread();
        try {
            withThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void withThread() throws InterruptedException {
        long start = System.currentTimeMillis();
        double[] arr = new double[100_000_000];


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < arr.length / 4; i++) {
                arr[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = arr.length / 4; i < arr.length / 2; i++) {
                arr[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = arr.length / 2; i < arr.length / 4 * 3; i++) {
                arr[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = arr.length / 4 * 3; i < arr.length; i++) {
                arr[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        long end = System.currentTimeMillis();
        System.out.println("Время выполнения c 4 потоками: " + (end - start) + " мс");
    }

    private static void withoutThread() {
        long start = System.currentTimeMillis();
        double[] arr = new double[100_000_000];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }

        long end = System.currentTimeMillis();

        System.out.println("Время выполнения с 1 потоком: " + (end - start) + " мс");
    }
}
