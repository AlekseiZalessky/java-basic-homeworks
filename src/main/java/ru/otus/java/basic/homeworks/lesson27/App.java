package ru.otus.java.basic.homeworks.lesson27;

public class App {
    public static void main(String[] args) {
        Box<Apple> apples1 =  new Box<>(Apple.class);
        Box<Orange> oranges =  new Box<>(Orange.class);
        Box<Fruit> fruits =  new Box<>(Fruit.class);
        Box<Fruit> fruits2 =  new Box<>(Fruit.class);

        Apple apple1  = new Apple("apple1",100);
        Apple apple2 = new Apple("apple2", 120);

        Orange orange1 = new Orange("orange1",95);
        Orange orange2 = new Orange("orange2",113);

        apples1.add(apple1);
        apples1.add(apple2);

        oranges.add(orange1);
        oranges.add(orange2);

        Apple apple3 = new Apple("apple3",110);
        Orange orange3 = new Orange("orange3",125);
        Fruit fruit1 = new Fruit("fruits1", 120);
        fruits.add(apple3);
        fruits.add(orange3);
        fruits.add(fruit1);

        System.out.println(apples1.weight());
        System.out.println(oranges.weight());
        System.out.println(fruits.weight());

        System.out.println(apples1.compare(oranges));

        System.out.println("fruitsBox: " + fruits.getItems());
        System.out.println("apples1: " + apples1.getItems());
        System.out.println("oranges: " + oranges.getItems());
        System.out.println();

        Box<Apple> apples2 =  new Box<>(Apple.class);

//        apples1.changeBox(apples2);
        fruits.changeBox(apples1);
//        fruits.changeBox(oranges);
//        fruits.changeBox(fruits2);
//        apples1.changeBox(fruits);
//        apples1.changeBox(oranges);

        System.out.println("fruitsBox: " + fruits.getItems());
        System.out.println("apples1: " + apples1.getItems());
        System.out.println("apples2: " + apples2.getItems());
        System.out.println("oranges: " + oranges.getItems());

    }
}
