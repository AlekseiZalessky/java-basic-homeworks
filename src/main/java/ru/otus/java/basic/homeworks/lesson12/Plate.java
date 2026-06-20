package ru.otus.java.basic.homeworks.lesson12;

public class Plate {
    private int maxFood;
    private int currentFood;

    public Plate(int maxFood) {
        this.maxFood = maxFood;
        this.currentFood = maxFood;
    }

    public void addFood(int amount) {
        if (currentFood < maxFood) {
            if (currentFood + amount > maxFood) {
                System.out.println("В тарелку смогли добавить " + (maxFood - currentFood) + " еды");
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
