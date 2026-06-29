package ru.otus.java.basic.homeworks.lesson11.animals;

public class Horse extends Animal{

    public Horse(String name, int runSpeed, int swimSpeed) {
        super(name, runSpeed, swimSpeed);
        this.swimStaminaCost = 4;
    }
}
