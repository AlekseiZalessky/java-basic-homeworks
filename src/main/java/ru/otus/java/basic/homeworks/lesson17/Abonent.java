package ru.otus.java.basic.homeworks.lesson17;

import java.util.Objects;

public class Abonent {
    private String firstName;
    private String lastName;
    private String email;

    public Abonent(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Abonent abonent = (Abonent) o;
        return Objects.equals(firstName, abonent.firstName) && Objects.equals(lastName, abonent.lastName) && Objects.equals(email, abonent.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
