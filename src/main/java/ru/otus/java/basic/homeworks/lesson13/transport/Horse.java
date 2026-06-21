package ru.otus.java.basic.homeworks.lesson13.transport;

public class Horse implements Transport {
    private int maxDistance;
    private Location location;
    private int maxStamina;
    private int currentStamina;
    private int staminaConsumptionPer100km;

    public Horse(int maxStamina, int staminaConsumptionPer100km) {
        this.maxStamina = maxStamina;
        this.staminaConsumptionPer100km = staminaConsumptionPer100km;
        this.currentStamina = maxStamina;
        maxDistance();
    }

    @Override
    public boolean move(int distance, Location location) {
        if (location.equals(Location.SWAMP)) {
            System.out.println("Лошадь может передвигаться по равнине или лесу. Используйте другой транспорт");
            return false;
        }
        if (distance > maxDistance) {
            System.out.println("Полностью отдохнувшая лошадь может проехать  " + maxDistance + " км");
            return false;
        }

        int stamina = calculateResourceCost(distance);
        if (currentStamina < stamina) {
            System.out.println("Недостаточно энергии, чтобы проехать " + distance + " км");
            return false;
        }
        currentStamina -= stamina;
        System.out.println("Лошадь успешно проехала " + distance + " км и потратила " + stamina + " единиц энергии.");
        System.out.println("Осталось единиц энергии: " + currentStamina);


        return true;
    }

    @Override
    public void maxDistance() {
        maxDistance = maxStamina / staminaConsumptionPer100km * 100;
    }

    @Override
    public int calculateResourceCost(int distance) {
        return distance * staminaConsumptionPer100km / 100;
    }
}
