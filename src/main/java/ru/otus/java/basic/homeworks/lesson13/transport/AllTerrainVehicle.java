package ru.otus.java.basic.homeworks.lesson13.transport;

public class AllTerrainVehicle implements Transport {
    private int maxDistance;
    private Location location;
    private int maxFuel;
    private int currentFuel;
    private int fuelConsumptionPer100km;

    public AllTerrainVehicle(int maxFuel, int fuelConsumptionPer100km) {
        this.maxFuel = maxFuel;
        this.fuelConsumptionPer100km = fuelConsumptionPer100km;
        this.currentFuel = maxFuel;
        maxDistance();
    }

    @Override
    public boolean move(int distance, Location location) {

        if (distance > maxDistance) {
            System.out.println("Вездеход на полном баке может проехать  " + maxDistance + " км");
            return false;
        }

        int fuel = calculateResourceCost(distance);
        if (currentFuel < fuel) {
            System.out.println("Недостаточно топлива, чтобы проехать " + distance + " км");
            return false;
        }
        currentFuel -= fuel;
        System.out.println("Вездеход успешно проехал " + distance + " км и потратил " + fuel + " литров топлива.");
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
