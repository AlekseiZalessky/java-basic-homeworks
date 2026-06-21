package ru.otus.java.basic.homeworks.lesson13.transport;

import ru.otus.java.basic.homeworks.lesson13.Human;

public class Main {
    public static void main(String[] args) {
        Car car = new Car(60, 7);
        Human human = new Human("Mike");

//        human.enter(car);
//        human.move(30, Location.SWAMP);
//
//        AllTerrainVehicle allTerrainVehicle = new AllTerrainVehicle(70, 20);
//        human.enter(allTerrainVehicle);
//
//        human.exit();
//        human.enter(allTerrainVehicle);
//        human.move(130, Location.SWAMP);
//        human.move(130, Location.PLAINS);
//        human.move(80, Location.DENSE_FOREST);
//        human.move(15, Location.PLAINS);
//
//        Horse horse = new Horse(100, 100);
//        human.enter(horse);
//        human.exit();
//        human.enter(horse);
//
//        human.move(100, Location.PLAINS);
//        human.move(50, Location.PLAINS);
        Bicycle bicycle = new Bicycle();
        human.enter(bicycle);

        human.move(100, Location.PLAINS);
        human.move(0, Location.PLAINS);

    }
}
