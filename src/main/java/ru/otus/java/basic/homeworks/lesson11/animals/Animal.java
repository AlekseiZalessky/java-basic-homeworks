package ru.otus.java.basic.homeworks.lesson11.animals;

public abstract class Animal {
    String name;
    int runSpeed;
    int swimSpeed;
    int stamina;
    int swimStaminaCost;
    int runStaminaCost;

    public Animal(String name, int runSpeed, int swimSpeed) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное имя");
        }
        if(runSpeed < 0) {
            throw new IllegalArgumentException("Некорректная скорость бега");
        }
        if(swimSpeed < 0) {
            throw new IllegalArgumentException("Некорректная скорость плавания");
        }
        this.name = name;
        this.runSpeed = runSpeed;
        this.swimSpeed = swimSpeed;
        this.stamina = 100;
        this.runStaminaCost = 1;
    }

    public float run(int distance) {
        if (!isValidDistanceAndSpeed(distance, runSpeed)) {
            return -1;
        }

        int newStamina = stamina - distance * runStaminaCost;
        if (isNegativeStamina(newStamina)) {
            return -1;
        }

        float time = (float) distance / runSpeed;
        stamina = newStamina;

        System.out.println("Животному по кличке " + name + " понадобилось " + time + " секунд, чтобы пробежать " + distance + " метров.");

        if (newStamina == 0) {
            System.out.println(name + " устал");
        } else {
            System.out.println("Осталось " + stamina + " единиц выносливости.");
        }

        return time;
    }

    public float swim(int distance) {
        if (!isValidDistanceAndSpeed(distance, swimSpeed)) {
            return -1;
        }

        int newStamina = stamina - distance * swimStaminaCost;
        if (isNegativeStamina(newStamina)) {
            return -1;
        }

        float time = (float) distance / swimSpeed;
        stamina = newStamina;

        System.out.println("Животному по кличке " + name + " понадобилось " + time + " секунд, чтобы проплыть " + distance + " метров.");

        if (newStamina == 0) {
            System.out.println(name + " устал");
        } else {
            System.out.println("Осталось " + stamina + " единиц выносливости.");
        }

        return time;
    }

    private boolean isValidDistanceAndSpeed(int distance, int speed) {
        if (distance < 0) {
            System.out.println("Некорректное значение дистанции");
            return false;
        }
        if (speed <= 0) {
            System.out.println("Некорректное значение скорости");
            return false;
        }
        return true;
    }

    private boolean isNegativeStamina(int stamina) {
        if (stamina < 0) {
            System.out.println(name + " устал");
            this.stamina = 0;
            return true;
        }
        return false;
    }

    public void info() {
        if (stamina > 0) {
            System.out.println("У животного по кличке " + name + " осталось " + stamina + " выносливости");
        } else {
            System.out.println(name + " устал");
        }
    }
}
