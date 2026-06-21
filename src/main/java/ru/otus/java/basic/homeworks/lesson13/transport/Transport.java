package ru.otus.java.basic.homeworks.lesson13.transport;

public interface Transport {
    boolean move(int distance, Location location);

    void maxDistance();

    int calculateResourceCost(int distance);
}
