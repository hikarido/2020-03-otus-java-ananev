package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;

import java.time.LocalTime;

public class ProcessorWhichThrowsExceptionsAtEachEvenSecond implements Processor {

    private Processor processor;

    public ProcessorWhichThrowsExceptionsAtEachEvenSecond(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        var now = LocalTime.now();
        boolean isItTimeToThrow = now.getSecond() % 2 == 0;
        if (isItTimeToThrow) {
            throw new TimeIsEvenException(now);
        }

        return processor.process(message);
    }
}
