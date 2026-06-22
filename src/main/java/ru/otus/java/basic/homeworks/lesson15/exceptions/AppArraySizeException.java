package ru.otus.java.basic.homeworks.lesson15.exceptions;

public class AppArraySizeException extends RuntimeException {
    public AppArraySizeException(String message) {
        super(message);
    }
}
