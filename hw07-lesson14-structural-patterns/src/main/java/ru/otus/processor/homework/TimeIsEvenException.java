package ru.otus.processor.homework;

import java.time.LocalTime;

public class TimeIsEvenException extends RuntimeException {
    private LocalTime time;
    public TimeIsEvenException(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
