package ru.otus.java.basic.homeworks.lesson12;

public class Plate {
    private int maxFood;
    private int currentFood;

    public Plate(int maxFood) {
        if (maxFood <= 0) {
            throw new IllegalArgumentException("Максимальное количество должно быть больше нуля");
        }
        this.maxFood = maxFood;
        this.currentFood = maxFood;
    }

    public void addFood(int amount) {
        if (currentFood < maxFood) {
            if (currentFood + amount > maxFood) {
                System.out.println("Тарелка заполнена до максимума, излишек " + (amount + currentFood - maxFood));
                currentFood = maxFood;
            } else {
                currentFood += amount;
                System.out.println("В тарелку добавили " + amount + " еды");
            }
        } else {
            System.out.println("В тарелку нельзя добавить еду. Она уже наполнена");
        }
    }

    public boolean removeFood(int amount) {
        if (currentFood == 0) {
            System.out.println("В тарелке нет еды");
            return false;
        }

        if (currentFood - amount < 0) {
            System.out.println("В тарелке нет такого количества еды");
            return false;
        }

        currentFood -= amount;
        return true;
    }

    public int getCurrentFood() {
        return currentFood;
    }
}
