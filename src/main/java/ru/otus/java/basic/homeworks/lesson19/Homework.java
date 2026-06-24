package ru.otus.java.basic.homeworks.lesson19;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homework {
    public static void main(String[] args) {
        File dir = new File(".");
        File[] files = dir.listFiles();

        List<String> listOfFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    listOfFiles.add(file.getName());
                }
            }
        }

        if (listOfFiles.isEmpty()) {
            System.out.println("Нет текстовых файлов");
            return;
        }

        for (int i = 0; i < listOfFiles.size(); i++) {
            System.out.println((i + 1) + ". " + listOfFiles.get(i));
        }

        int number = 0;
        String fileName = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите номер файла для работы");
        while (true) {

            if (sc.hasNextInt()) {
                number = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Вы ввели не число");
                sc.nextLine();
                continue;
            }

            if (number > 0 && number <= listOfFiles.size()) {
                fileName = listOfFiles.get(number - 1);
                break;
            } else {
                System.out.println("Введите корректный номер файла");
                System.out.println();
            }
        }

        System.out.println("Содержимое файла " + fileName + ":");
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(fileName))) {

            int n = in.read();
            while (n != -1) {
                System.out.print((char) n);
                n = in.read();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n");
        System.out.println("Введите строку для записи в текущий файл:");
        String inputString = sc.nextLine();


        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName, true))) {
            byte[] buffer = inputString.getBytes(StandardCharsets.UTF_8);

            out.write(buffer);
            out.write(System.lineSeparator().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
