package ru.otus.java.basic.homeworks.lesson31;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    @DisplayName("Должен выкинуть исключение IllegalArgumentException есди пришел null")
    void returnArrayAfterOne_IllegalArgumentExceptionNull() {
        assertThrows(IllegalArgumentException.class, () -> App.returnArrayAfterOne(null));
    }

    @Test
    @DisplayName("Должен выкинуть IllegalArgumentException если массив пустой")
    void returnArrayAfterOne_IllegalArgumentExceptionEmpty() {
        assertThrows(IllegalArgumentException.class, () -> App.returnArrayAfterOne(new int[]{}));
    }

    @Test
    @DisplayName("Должен вернуть массив после последней единицы")
    void returnArrayAfterOne() {
        assertArrayEquals(new int[]{2, 2}, App.returnArrayAfterOne(new int[]{1, 2, 1, 2, 2}));
    }

    @Test
    @DisplayName("Должен вернуть пустой массив, если во входном массиве последняя единица")
    void returnArrayAfterOne_returnEmptyArray() {
        assertArrayEquals(new int[]{}, App.returnArrayAfterOne(new int[]{1, 2, 1, 2, 2, 1}));
    }

    @Test
    @DisplayName("Должен выкинуть RuntimeException если массив не содержит хотя бы одной 1")
        void returnArrayAfterOne_RuntimeException() {
        assertThrows(RuntimeException.class, () -> App.returnArrayAfterOne(new int[]{2, 2, 2}));
    }





    @Test
    @DisplayName("Должен выкинуть IllegalArgumentException если пришел null")
    void containsOnlyOneAndTwo_IllegalArgumentExceptionNull() {
        assertThrows(IllegalArgumentException.class, ()-> App.containsOnlyOneAndTwo(null));
    }

    @Test
    @DisplayName("Должен выкинуть IllegalArgumentException если пришел пустой массив")
    void containsOnlyOneAndTwo_IllegalArgumentExceptionEmpty() {
        assertThrows(IllegalArgumentException.class, () -> App.containsOnlyOneAndTwo(new int[]{}));
    }

    @Test
    @DisplayName("Возвращает true если состоит только из 1 и 2")
    void containsOnlyOneAndTwo() {
        assertTrue(App.containsOnlyOneAndTwo(new int[]{1, 2, 2}));
    }

    @Test
    @DisplayName("Возвращает false если есть что-либо кроме 1 и 2")
    void containsOnlyOneAndTwo_returnFalseIfNotOnly1And2() {
        assertFalse(App.containsOnlyOneAndTwo(new int[]{1, 2, 3}));
    }

    @Test
    @DisplayName("Возвращает false если нет хотя бы одной 1 и одной 2")
    void containsOnlyOneAndTwo_returnFalseIfNot1And2() {
        assertFalse(App.containsOnlyOneAndTwo(new int[]{1, 1}));
    }
}