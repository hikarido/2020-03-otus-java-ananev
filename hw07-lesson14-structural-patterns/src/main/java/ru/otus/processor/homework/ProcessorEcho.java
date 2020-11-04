package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;

/**
 * echo as echo server.
 */
public class ProcessorEcho implements Processor {
    @Override
    public Message process(Message message) {
        return message;
    }
}
