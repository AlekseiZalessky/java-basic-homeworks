package ru.otus.java.basic.homeworks.lesson27;

import lombok.Data;

@Data
public class Fruit {
    protected String name;
    protected int weight;

    public Fruit(String name, int weight) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Название фрукта не может быть пустым");
        }
        if (weight <= 0){
            throw new IllegalArgumentException("Вес должен быть больше нуля");
        }
        this.weight = weight;
        this.name = name;
    }
}
