package ru.otus.java.basic.homeworks.lesson11;

import ru.otus.java.basic.homeworks.lesson11.animals.Cat;
import ru.otus.java.basic.homeworks.lesson11.animals.Dog;
import ru.otus.java.basic.homeworks.lesson11.animals.Horse;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Пушок", -2);
        Dog dog = new Dog("Бобик", -4, 2);
        Horse horse = new Horse("Зорька", 6, 1);

        dog.swim(10);
    }
}
