package ru.otus.listener.homework;

import ru.otus.Message;
import ru.otus.listener.Listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class ListenerHistorian implements Listener {
    private final Path whereToSave;

    public ListenerHistorian(Path whereToSaveHistory) {
        whereToSave = whereToSaveHistory;

        if (!Files.exists(whereToSave)) {
            try {
                Files.createFile(whereToSave);
            } catch (IOException e) {
                throw new RuntimeException("File " + whereToSave.toString() + " can't be created");
            }
        }
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        byte[] data = makeBytesOf(oldMsg, newMsg);
        try {
            Files.write(whereToSave, data, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] makeBytesOf(Message oldMsg, Message newMsg) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        builder.append(LocalDateTime.now().toString());
        builder.append(System.lineSeparator());
        builder.append("OLD");
        builder.append(System.lineSeparator());
        builder.append(oldMsg.toString());
        builder.append(System.lineSeparator());
        builder.append("NEW");
        builder.append(System.lineSeparator());
        builder.append(newMsg.toString());
        builder.append(System.lineSeparator());
        return builder.toString().getBytes();
    }
}
