package ru.otus.java.basic.homeworks.lesson12;

public class Cat {
    private String name;
    private int appetite;
    private boolean full;

    public Cat(String name, int appetite) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное имя");
        }
        if (appetite <= 0 ) {
            throw new IllegalArgumentException("Некорректное значение аппетита");
        }
        this.name = name;
        this.appetite = appetite;
    }

    public void eat(Plate plate) {
        if (full) {
            System.out.println(name + " не голоден");
            return;
        }

        if (plate.removeFood(appetite)) {
            full = true;
            System.out.println(name + " покушал");
        }
    }

    public void info() {
        if (full) {
            System.out.println(name + " сытый");
        } else {
            System.out.println(name + " голодный");
        }
    }

    public boolean isFull() {
        return full;
    }

    public String getName() {
        return name;
    }
}
