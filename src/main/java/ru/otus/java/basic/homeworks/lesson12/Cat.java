package ru.otus.java.basic.homeworks.lesson12;

public class Cat {
    private String name;
    private int appetite;
    private boolean full;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void eat(Plate plate) {
        if (full) {
            System.out.println(name + " не голоден");
            return;
        }
        if (plate.getCurrentFood() < appetite) {
            System.out.println("В терелке недостаточно еды, " + name + " остался голодным");
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
