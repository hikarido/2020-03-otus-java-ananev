package ru.otus.hw3.assertions;

public class Assertions {
    public static void assertTrue(boolean flag){
        if(flag != true){
            throw new AssertionBaseException("Must be true");
        }
    }
}
