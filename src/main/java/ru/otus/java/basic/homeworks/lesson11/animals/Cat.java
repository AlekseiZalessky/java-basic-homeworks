package ru.otus.java.basic.homeworks.lesson11.animals;

public class Cat extends Animal{
    public Cat(String name, int runSpeed) {
        super(name, runSpeed, 0);
    }

    @Override
    public float swim(int distance) {
        System.out.println("Кот плавать не умеет");
        return -1;
    }
}
