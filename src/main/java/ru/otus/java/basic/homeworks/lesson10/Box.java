package ru.otus.java.basic.homeworks.lesson10;

public class Box {
    private final int length;
    private final int width;
    private final int height;
    private String color;
    private boolean open;
    private String item;

    public Box(int length, int width, int height, String color) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.color = color;
        this.item = "";
    }

    public void open() {
        if (!open) {
            open = true;
            System.out.println("Коробка успешно открыта");
        } else {
            System.out.println("Коробка уже открыта");
        }
    }

    public void close() {
        if (open) {
            open = false;
            System.out.println("Коробка успешно закрыта");
        } else {
            System.out.println("Коробка уже закрыта");
        }
    }

    public void reColor(String color) {
        if (!this.color.equals(color)) {
            this.color = color;
            System.out.println("Коробка перекрашена в " + color + " цвет");
        } else {
            System.out.println("Коробка уже покрашена в " + color + " цвет");
        }
    }

    public void info() {
        System.out.println("Длина: " + length);
        System.out.println("Ширина: " + width);
        System.out.println("Высота: " + height);
        System.out.println("Цвет: " + color);
        if (open) {
            System.out.println("Коробка открыта");
        } else {
            System.out.println("Коробка закрыта");
        }
    }

    public void addItem(String item) {
        if (!open) {
            System.out.println("Коробка закрыта. Чтобы положить предмет - сначала ее откройте");
            return;
        }

        if ("".equals(this.item)) {
            this.item = item;
            System.out.println("В коробку положили " + item);
        } else {
            System.out.println("Коробка не пустая, в ней находится " + this.item);
        }
    }

    public void removeItem() {
        if (!open) {
            System.out.println("Коробка закрыта. Чтобы достать предмет - сначала ее откройте");
            return;
        }

        if (!"".equals(item)) {
            System.out.println("Коробка освобождена");
            this.item = "";
        } else {
            System.out.println("Коробка уже пуста");
        }
    }
}
