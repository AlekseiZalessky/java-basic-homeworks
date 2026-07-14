package ru.otus.java.basic.homeworks.lesson29;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        String stringForMatches = "";

        while (true) {
            System.out.println("Введите имя файла в формате fileName.txt");
            fileName = scanner.nextLine();
            if (!fileName.isBlank()) {
                break;
            }
            System.out.println("Имя файла не может быть пустым");
        }

        while (true) {
            System.out.println("Введите искомую последовательность");
            stringForMatches = scanner.nextLine();
            if (!stringForMatches.isEmpty()) {
                break;
            }
            System.out.println("Необходимо ввести хотя бы один символ");
        }

        int countMatches = countMatches(fileName, stringForMatches);
        System.out.println("Количество совпадений: " + countMatches);
    }

    public static int countMatches(String fileName, String stringForMatches) {
        int count = 0;
        boolean flag = false;
        String foundString = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            int c;
            while ((c = br.read()) != -1) {
                if (!flag && (char) c == stringForMatches.charAt(0)) {
                    flag = true;
                    foundString = String.valueOf((char) c);
                    if (stringForMatches.length() > 1) {
                        continue;
                    }
                }
                if (flag && foundString.length() <= stringForMatches.length()) {
                    if (stringForMatches.length() > 1) {
                        foundString += (char) c;
                    }
                    if (stringForMatches.equals(foundString)) {
                        count++;
                        flag = false;
                        foundString = "";
                    }
                } else {
                    flag = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}
