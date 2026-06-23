package ru.otus.java.basic.homeworks.lesson17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private Map<Abonent, List<String>> phoneBook = new HashMap<>();

    public void add(String phoneNumber, Abonent abonent) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Некорректный телефон");
        }
        if (abonent == null) {
            throw new IllegalArgumentException("Некорректный абонент");
        }

        Abonent ab = null;
        if (containsPhoneNumber(phoneNumber)) {
            for (Map.Entry<Abonent, List<String>> entry : phoneBook.entrySet()) {
                for (String number : entry.getValue()) {
                    if (number.equals(phoneNumber)) {
                        ab = entry.getKey();
                        break;
                    }
                }
            }

            if(ab != null && !ab.equals(abonent)) {
                System.out.println("Данный номер занят. Принадлежит абоненту: " + ab);
                return;
            }
            if (ab != null && ab.equals(abonent)) {
                System.out.println("Номер уже принадлежит текущему абоненту: " + ab);
                return;
            }
        }

        List<String> numbers = new ArrayList<>();

        if (ab != null) {
            numbers = phoneBook.get(ab);
        }

        numbers.add(phoneNumber);

        phoneBook.put(abonent, numbers);
        System.out.println("Номер: " + phoneNumber + " успешно добавлен абоненту: " + abonent);
    }

    public List<String> find(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Некорректные данные");
        }

        List<String> phoneNumbers = new ArrayList<>();


        for(Map.Entry<Abonent, List<String>> entry : phoneBook.entrySet()) {
            if(entry.getKey().getFirstName().equals(firstName) && entry.getKey().getLastName().equals(lastName)) {
                for(String number : entry.getValue()) {
                    phoneNumbers.add(number);
                }
            }
        }

        return phoneNumbers;
    }


    public List<String> find(Abonent abonent) {
        if (abonent == null) {
            throw new IllegalArgumentException("Некорректный абонент");
        }
        List<String> phoneNumbers = new ArrayList<>();

        for (Map.Entry<Abonent, List<String>> entry : phoneBook.entrySet()) {
            if (entry.getKey().equals(abonent)) {
                for(String number : entry.getValue()) {
                    phoneNumbers.add(number);
                }
            }
        }
        return phoneNumbers;
    }

    public boolean containsPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Некорректный номер телефона");
        }

        for (Map.Entry<Abonent, List<String>> entry : phoneBook.entrySet()) {
            if (entry.getValue().contains(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
}
