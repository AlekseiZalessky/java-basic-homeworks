package ru.otus.java.basic.homeworks.lesson13;

import ru.otus.java.basic.homeworks.lesson13.transport.*;

public class Human {
    private String name;
    private Transport currentTransport;
    private int stamina;
    private final int COST_WALK = 2;
    private final int COST_MOVE_BICYCLE = 1;

    public Human(String name) {
        this.name = name;
        this.stamina = 100;
    }

    public void enter(Transport transport) {
        String transportToString = getTransportToString(transport);
        if (transportToString == null) {
            System.out.println("Вы не можеете использовать неизвестный транспорт");
            return;
        }
        if (currentTransport != null) {
            System.out.println(" Нельзя использовать транспорт " + transportToString +
                    ". сначала нужно покинуть текущий транспорт: " + getTransportToString(currentTransport));
            return;
        }
        currentTransport = transport;
        System.out.println("Вы успешно сели на транспорт: " + transportToString);
    }

    public void exit() {
        if (currentTransport == null) {
            System.out.println("Покинуть транпорт нельзя - вы не используете никакой транспорт");
            return;
        }
        System.out.println("Вы успешно покинули транспорт: " + getTransportToString(currentTransport));
        currentTransport = null;
    }

    public void move(int distance, Location location) {
        if (currentTransport != null) {
            if (!(currentTransport instanceof Bicycle)) {
                currentTransport.move(distance, location);
            } else {
                int staminaRequired = calculateStamina(distance, COST_MOVE_BICYCLE);
                if (stamina < staminaRequired) {
                    System.out.println("Энергии не хватит, чтобы проехать " + distance + " км");
                    return;
                }
                if (stamina == 0) {
                    System.out.println(name + " устал, и не может ехать на велосипеде");
                    return;
                }

                if (currentTransport.move(distance, location)) {
                    stamina -= staminaRequired;
                    System.out.println(name + " проехал на велосипеде " + distance + " км");
                    System.out.println("Затрачено энергии " + staminaRequired + ". Осталось энергии " + stamina);
                }
            }
        } else {
            int staminaRequired = calculateStamina(distance, COST_WALK);
            if (stamina >= staminaRequired) {
                stamina -= staminaRequired;
                System.out.println(name + " успешно прошел " + distance + " км. Потратив " + staminaRequired + " энергии");
                System.out.println("Оставшееся энергия : " + stamina);

            } else if (stamina == 0) {
                System.out.println(name + " не может идти пешком - он устал.");
            } else {
                System.out.println("Пешком " + name + " не сможет пройти " + distance + " км");
            }
        }
    }

    public int calculateStamina(int distance, int cost) {
        return cost * distance;
    }

    private String getTransportToString(Transport transport) {
        switch (transport) {
            case Car car:
                return "машина";
            case AllTerrainVehicle allTerrainVehicle:
                return "вездеход";
            case Horse horse:
                return "лошадь";
            case Bicycle bicycle:
                return "велосипед";
            default:
                System.out.println(" Неизвестный транспорт");
                return null;
        }
    }
}
