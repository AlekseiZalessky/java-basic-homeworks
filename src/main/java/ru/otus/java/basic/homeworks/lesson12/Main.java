package ru.otus.java.basic.homeworks.lesson12;

public class Main {
    public static void main(String[] args) {
        Plate plate = new Plate(50);

        Cat[] cats = new Cat[]{
                new Cat("Васька", 11),
                new Cat("Барсик", 10),
                new Cat("Тоша", 14),
                new Cat("Маня", 8),
                new Cat("Валерон", 10),
        };

        eat(cats, plate);

        info(cats);

        plate.addFood(15);

        eat(cats, plate);

        info(cats);
    }

    private static void info(Cat[] cats) {
        for (Cat cat : cats) {
            cat.info();
        }
    }

    private static void eat(Cat[] cats, Plate plate) {
        for (Cat cat : cats) {
            cat.eat(plate);
        }
    }
}
