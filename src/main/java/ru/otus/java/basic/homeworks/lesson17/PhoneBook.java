package ru.otus.java.basic.homeworks.lesson17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private Map<String, Abonent> phoneBook;

    public void add(String phoneNumber, Abonent abonent) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Некорректный телефон");
        }
        if (abonent == null) {
            throw new IllegalArgumentException("Некорректный абонент");
        }

        if (phoneBook == null) {
            phoneBook = new HashMap<>();
        }

        if (containsPhoneNumber(phoneNumber)) {
            System.out.println("Данный номер занят. Принадлежит абоненту: " + phoneBook.get(phoneNumber));
            return;
        }

        phoneBook.put(phoneNumber, abonent);
    }

    public List<String> getPhoneNumbers(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Некорректные данные");
        }

        List<String> phoneNumbers = new ArrayList<>();

        for (Map.Entry<String, Abonent> entry : phoneBook.entrySet()) {
            if (entry.getValue().getFirstName().equals(firstName) && entry.getValue().getLastName().equals(lastName)) {
                phoneNumbers.add(entry.getKey());
            }
        }
        return phoneNumbers;
    }


    public List<String> getPhoneNumbers(Abonent abonent) {
        if (abonent == null) {
            throw new IllegalArgumentException("Некорректный абонент");
        }
        List<String> phoneNumbers = new ArrayList<>();
        for (Map.Entry<String, Abonent> entry : phoneBook.entrySet()) {
            if (entry.getValue().equals(abonent)) {
                phoneNumbers.add(entry.getKey());
            }
        }
        return phoneNumbers;
    }

    public boolean containsPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Некорректный номер телефона");
        }
        return phoneBook.containsKey(phoneNumber);
    }
}
