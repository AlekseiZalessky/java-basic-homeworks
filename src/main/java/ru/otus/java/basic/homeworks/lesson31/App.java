package ru.otus.java.basic.homeworks.lesson31;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
    }

    public static int[] returnArrayAfterOne(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr is null");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("arr is empty");
        }

        boolean isOne = false;
        List<Integer> list = new ArrayList<>();

        for (int i : arr) {
            if (i == 1) {
                isOne = true;
                list.clear();
                continue;
            }
            list.add(i);
        }
        if (!isOne) {
            throw new RuntimeException("Массив не содержит единиц");
        }
        int[] arrayToReturn = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            arrayToReturn[i] = list.get(i);
        }
        return arrayToReturn;
    }

    public static boolean containsOnlyOneAndTwo(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr is null");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("arr is empty");
        }
        boolean isOne = false;
        boolean isTwo = false;

        for (int i : arr) {
            if (i == 1) {
                isOne = true;
            } else if (i == 2) {
                isTwo = true;
            } else {
                return false;
            }
        }
        return isOne && isTwo;
    }
}
