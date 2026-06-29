package ru.otus.java.basic.homeworks.lesson10;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        User[] users = new User[]{
                new User("Иван", "Иванович", "Иванов", 1990, "ivan.ivanov@mail.ru"),
                new User("Петр", "Петрович", "Петров", 1985, "petr.petrov@yandex.ru"),
                new User("Елена", "Сергеевна", "Смирнова", 1992, "elena.smirnova@gmail.com"),
                new User("Алексей", "Алексеевич", "Алексеев", 1988, "aleksei.alekseev@mail.ru"),
                new User("Мария", "Владимировна", "Кузнецова", 1995, "maria.kuznetsova@mail.ru"),
                new User("Дмитрий", "Дмитриевич", "Дмитриев", 1980, "dmitry.dmitriev@yandex.ru"),
                new User("Ольга", "Александровна", "Павлова", 1993, "olga.pavlova@gmail.com"),
                new User("Сергей", "Михайлович", "Михайлов", 1987, "sergey.mikhailov@mail.ru"),
                new User("Анна", "Викторовна", "Соколова", 1991, "anna.sokolova@mail.ru"),
                new User("Николай", "Николаевич", "Николаев", 1983, "nikolay.nikolaev@mail.ru")
        };

        for (User user : users) {
            int age = LocalDate.now().getYear() - user.getBirthYear();
            if (age > 40) {
                user.info();
                System.out.println();
            }
        }
    }
}
