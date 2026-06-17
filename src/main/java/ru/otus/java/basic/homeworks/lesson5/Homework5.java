package ru.otus.java.basic.homeworks.lesson5;

import java.util.Arrays;

public class Homework5 {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5, -6, 7, 8};
        int[] arr2 = {4, -6, 8, -4, 5, 2};
        int[] arr3 = {4, -5, 1, 0, -7, 9, -8, 2, -9, 10};

        sumArrays(arr1, arr2, arr3);

        point(5, 3, 4, -2, 2);

        sorted(arr1);

        reverse(arr3);
    }

    public static void sumArrays(int[] arr1, int[] arr2, int[] arr3) {
        int maxLength = Math.max(Math.max(arr1.length, arr2.length), arr3.length);
        int[] resultArr = new int[maxLength];

        for (int i = 0; i < maxLength; i++) {
            if (i < arr1.length) {
                resultArr[i] += arr1[i];
            }
            if (i < arr2.length) {
                resultArr[i] += arr2[i];
            }
            if (i < arr3.length) {
                resultArr[i] += arr3[i];
            }
        }
        System.out.println(Arrays.toString(resultArr));
        System.out.println();
    }

    public static void point(int... arr) {
        boolean flag = false;

        for (int i = 1; i < arr.length; i++) {
            int sumLeft = 0;
            int sumRight = 0;

            for (int j = 0; j < i; j++) {
                sumLeft += arr[j];
            }
            for (int j = i; j < arr.length; j++) {
                sumRight += arr[j];
            }

            if (sumLeft == sumRight) {
                flag = true;
                System.out.println("Точка находится между индексами: " + (i - 1) + " и " + i);
                break;
            }
        }
        if (!flag) {
            System.out.println("Точки нет");
        }
        System.out.println();
    }

    public static void sorted(int[] array) {
        boolean sorted = false;

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] <= array[i + 1]) {
                sorted = true;
            } else {
                sorted = false;
                System.out.println("Not sorted");
                break;
            }
        }

        if (sorted) {
            System.out.println("Sorted");
        }
        System.out.println();
    }

    public static void reverse(int[] array) {
        int length = array.length;
        int[] reverse = new int[length];

        for (int i = 0; i < array.length; i++) {
            reverse[length - 1] = array[i];
            length--;
        }

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(reverse));
        System.out.println();
    }
}
