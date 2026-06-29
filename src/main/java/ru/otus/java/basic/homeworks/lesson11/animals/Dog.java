package ru.otus.java.basic.homeworks.lesson11.animals;

public class Dog extends Animal{

    public Dog(String name, int runSpeed, int swimSpeed) {
        super(name, runSpeed, swimSpeed);
        this.swimStaminaCost = 2;
    }
}
