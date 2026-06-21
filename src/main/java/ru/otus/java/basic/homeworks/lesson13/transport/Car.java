package ru.otus.java.basic.homeworks.lesson13.transport;

public class Car implements Transport {
    private int maxDistance;
    private Location location;
    private int maxFuel;
    private int currentFuel;
    private int fuelConsumptionPer100km;

    public Car(int maxFuel, int fuelConsumptionPer100km) {
        this.maxFuel = maxFuel;
        this.fuelConsumptionPer100km = fuelConsumptionPer100km;
        this.currentFuel = maxFuel;
        maxDistance();
    }

    @Override
    public boolean move(int distance, Location location) {
        if (location.equals(Location.DENSE_FOREST) || location.equals(Location.SWAMP)) {
            System.out.println("Машина может передвигаться только по равнине. Используйте другой транспорт");
            return false;
        }
        if (distance > maxDistance) {
            System.out.println("Машина на полном баке может проехать  " + maxDistance + " км");
            return false;
        }

        int fuel = calculateResourceCost(distance);
        if (currentFuel < fuel) {
            System.out.println("Недостаточно топлива, чтобы проехать " + distance + " км");
            return false;
        }
        currentFuel -= fuel;
        System.out.println("Машина успешно проехала " + distance + " км и потратила " + fuel + " литров топлива.");
        System.out.println("Осталось литров топлива: " + currentFuel);


        return true;
    }

    @Override
    public void maxDistance() {
        maxDistance = maxFuel / fuelConsumptionPer100km * 100;
    }

    @Override
    public int calculateResourceCost(int distance) {
        return distance * fuelConsumptionPer100km / 100;
    }
}
