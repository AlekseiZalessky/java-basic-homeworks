package ru.otus.java.basic.homeworks.lesson27;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Box<T extends Fruit> {
    private final List<T> items;
    private final Class<T> type;

    public Box(Class<T> type) {
        this.type = type;
        this.items = new ArrayList<>();
    }

    public void add(T t) {
        items.add(t);
    }

    public int weight() {
        int weight = 0;
        for (T item : items) {
            weight += item.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<?> box) {
        return this.weight() == box.weight();
    }

    public void changeBox(Box<? extends Fruit> box) {
        if (box == null) {
            throw new IllegalArgumentException("Некорректная коробка");
        }
        if (items.isEmpty()) {
            System.out.println("Текущая коробка пуста.");
            return;
        }

        if (this.getType().equals(box.getType()) || box.getType().equals(Fruit.class)) {
            Box<T> otherBox = (Box<T>) box;

            for (T item : items) {
                otherBox.add(item);
            }
            items.clear();
            System.out.println("Все фрукты успешно пересыпаны");
            return;
        }

        if (this.getType().equals(Fruit.class)) {
            List<T> clone = new ArrayList<>(items);
            for (T item : clone) {
                if(item.getClass().equals(box.getType())) {
                    Box<T> otherBox = (Box<T>) box;
                    otherBox.add(item);
                    items.remove(item);
                }
            }
            String fruit = "";

            if(box.getType().equals(Apple.class)) {
                fruit = "яблоки";
            }
            if(box.getType().equals(Orange.class)) {
                fruit = "апельсины";
            }

            System.out.println("Все " + fruit + " пересыпаны в новую коробку");
            return;
        }

        System.out.println("Нельзя пересыпать фрукты - не соответствующие коробки");
    }
}
