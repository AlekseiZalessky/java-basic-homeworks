package ru.otus.java.basic.homeworks.lesson10;

public class User {
    private String firstName;
    private String patronymic;
    private String lastName;
    private int birthYear;
    private String email;

    public User(String firstName, String patronymic, String lastName, int birthYear, String email) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.email = email;
    }

    public void info() {
        System.out.println("ФИО: " + firstName + " " + patronymic + " " + lastName);
        System.out.println("Год рождения: " + birthYear);
        System.out.println("e-mail: " + email);
    }

    public int getBirthYear() {
        return birthYear;
    }
}
