package ru.otus.java.basic.homeworks.lesson16;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jack", 55));
        employees.add(new Employee("James", 35));
        employees.add(new Employee("John", 22));
        employees.add(new Employee("James", 27));

        System.out.println(youngestEmployee(employees));
    }

    public static ArrayList<Integer> getDiap(int min, int max) {
        ArrayList<Integer> list = null;
        if (min > max) {
            throw new IllegalArgumentException("Максимальное число не может быть меньше минимального ");
        }

        if (min == max) {
            throw new IllegalArgumentException("Минимальное и максимальное значения не должны быть равны");
        }

        if (max - min > 100) {
            list = new ArrayList<>(100);
        } else {
            list = new ArrayList<>();
        }

        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        return list;
    }

    public static int sum(ArrayList<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }

        int sum = 0;
        for (Integer i : list) {
            if (i > 5){
                sum += i;
            }
        }
        return sum;
    }

    public static List<Integer> replace(ArrayList<Integer> list, int value) {
        if (list == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, value);
        }
        return list;
    }

    public static List<Integer> elementPlusValue(ArrayList<Integer> list, int value) {
        if (list == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + value);
        }
        return list;
    }

    public static List<String> getListNamesEmployee(List<Employee> listEmployees) {
        if (listEmployees == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }

        List<String> listNames = new ArrayList<>();
        for (Employee employee :  listEmployees) {
            listNames.add(employee.getName());
        }

        return listNames;
    }

    public static List<Employee> getListEmployees(List<Employee> listEmployees, int age) {
        if (listEmployees == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }
        List<Employee> list = new ArrayList<>();
        for (Employee employee : listEmployees) {
            if (employee.getAge() >= age) {
                list.add(employee);
            }
        }
        return list;
    }

    public static boolean averageAge(List<Employee> listEmployees, int age) {
        if (listEmployees == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }
        int sumAge = 0;
        for (Employee employee : listEmployees) {
            sumAge += employee.getAge();
        }
        double averageAge =(double) sumAge / listEmployees.size();

        return averageAge > age;
    }

    public static Employee youngestEmployee(List<Employee> listEmployees) {
        if (listEmployees == null) {
            throw new IllegalArgumentException("Список не проинициализирован");
        }
        if (listEmployees.isEmpty()) {
            return null;
        }
        int minAge = listEmployees.getFirst().getAge();
        Employee youngestEmployee = listEmployees.getFirst();
        for (Employee employee : listEmployees) {
            if (employee.getAge() <  minAge) {
                minAge = employee.getAge();
                youngestEmployee = employee;
            }
        }
        return youngestEmployee;
    }
}
