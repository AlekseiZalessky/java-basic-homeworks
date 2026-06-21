package ru.otus.java.basic.homeworks.lesson13.transport;

public class Bicycle implements Transport {
    private Location location;

    @Override
    public boolean move(int distance, Location location) {
        if (location.equals(Location.SWAMP)) {
            System.out.println("Велосипед может передвигаться по равнине или лесу. Используйте другой транспорт");
            return false;
        }

        System.out.println("Крутим педали");
        return true;
    }

    @Override
    public void maxDistance() {
        System.out.println("Для велосипеда не важна дистанция");
    }

    @Override
    public int calculateResourceCost(int distance) {
        System.out.println("Для велосипеда данный параметр не важен");
        return 0;
    }
}
