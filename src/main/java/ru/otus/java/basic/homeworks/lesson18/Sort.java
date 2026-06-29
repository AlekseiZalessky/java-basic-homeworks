package ru.otus.java.basic.homeworks.lesson18;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] arr = {6, 4, -8, 6, 2, -9, 8, 5, 1, 0, 6, 4, 3};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] array) {
        boolean change;

        while (true) {
            change = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    change = true;
                }
            }
            if (!change) {
                break;
            }
        }
    }

}
