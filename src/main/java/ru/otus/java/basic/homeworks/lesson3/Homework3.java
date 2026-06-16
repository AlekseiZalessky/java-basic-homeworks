package ru.otus.java.basic.homeworks.lesson3;

import java.util.Scanner;

public class Homework3 {
    public static void main(String[] args) {
        System.out.println("Java Basic Homeworks");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число от 1 до 5: ");
        int enteredNumber = scanner.nextInt();

        if (enteredNumber == 1) {
            greetings();
        } else if (enteredNumber == 2) {
            int a = (int) (Math.random() * 21) - 10;
            int b = (int) (Math.random() * 21) - 10;
            int c = (int) (Math.random() * 21) - 10;
            checkSign(a, b, c);
        } else if (enteredNumber == 3) {
            selectColor();
        } else if (enteredNumber == 4) {
            compareNumbers();
        } else if (enteredNumber == 5) {
            int initValue = (int) (Math.random() * 21) - 10;
            int delta = (int) (Math.random() * 21) - 10;
            boolean increment = Math.random() < 0.5;
            addOrSubtractAndPrint(initValue, delta, increment);
        } else {
            System.out.println("Вы ввели некорректное число");
        }
    }

    public static void greetings() {
        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");
    }

    public static void checkSign(int a, int b, int c) {
        int sum = a + b + c;
        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    public static void selectColor() {
        int data = (int) (Math.random() * 31);
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers() {
        int a = (int) (Math.random() * 21) - 10;
        int b = (int) (Math.random() * 21) - 10;
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        if (increment) {
            initValue += delta;
            System.out.println("initValue: " + initValue);
        } else {
            initValue -= delta;
            System.out.println("initValue: " + initValue);
        }
    }
}