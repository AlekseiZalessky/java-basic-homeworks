package ru.otus.java.basic.homeworks.lesson17;

public class Main {
    public static void main(String[] args) {
        Abonent abonent1 = new Abonent("Иван", "Иванов", "ivan_ivanov@mail.ru");
        Abonent abonent2 = new Abonent("Петр", "Петров", "p_petrov@mail.ru");
        Abonent abonent3 = new Abonent("Иван", "Петров", "ivan_petrov@mail.ru");
        Abonent abonent4 = new Abonent("Иван", "Иванов", "ivanov@mail.ru");

        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("+375299544544", abonent1);
        phoneBook.add("+375294587451", abonent2);
        phoneBook.add("+375295478541", abonent3);
        phoneBook.add("+375333335552", abonent4);
        phoneBook.add("+375254545655", abonent1);
        phoneBook.add("+375254545655", abonent1);

        System.out.println(phoneBook.find("Иван", "Иванов"));

        System.out.println(phoneBook.find(abonent1));
    }
}
