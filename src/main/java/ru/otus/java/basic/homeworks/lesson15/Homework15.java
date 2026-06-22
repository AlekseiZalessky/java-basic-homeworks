package ru.otus.java.basic.homeworks.lesson15;

import ru.otus.java.basic.homeworks.lesson15.exceptions.AppArrayDataException;
import ru.otus.java.basic.homeworks.lesson15.exceptions.AppArraySizeException;

public class Homework15 {
    public static void main(String[] args) {
        String[][] array = new String[][]{
                {"1", "1", "1", "1"},
                {"2", "2", "2", "2"},
                {"3", "3", "3", "3"},
                {"4", "4", "4", "4"},
        };

        int sum;

        try {
            sum = sumElements(array);
            System.out.println("Сумма элементов двумерного массива: " + sum);
        } catch (AppArraySizeException e) {
            e.printStackTrace();
        } catch (AppArrayDataException e) {
            e.printStackTrace();
        }


    }

    public static int sumElements(String[][] array) {
        if (array.length != 4 || array[0].length != 4) {
            throw new AppArraySizeException("Передаваемый массив должен быть размером 4х4");
        }

        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++){
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException(String.format("Элемент в ячейке [%d][%d] не является числом", i, j));
                }
            }
        }

        return sum;
    }
}
