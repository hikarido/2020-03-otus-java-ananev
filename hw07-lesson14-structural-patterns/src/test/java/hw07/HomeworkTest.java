package hw07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.Message;
import ru.otus.ObjectForMessage;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.ListenerHistorian;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorEcho;
import ru.otus.processor.homework.ProcessorSwapFields11And12;
import ru.otus.processor.homework.ProcessorWhichThrowsExceptionsAtEachEvenSecond;
import ru.otus.processor.homework.TimeIsEvenException;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeworkTest {
    @Test
    @DisplayName("Testing of swapping of field 11 and field 12. TODO № 2")
    void swapFieldsTest() {
        var processors
                = List.of((Processor) new ProcessorSwapFields11And12());
        Consumer<Exception> doNothing = (Exception ex) -> {
        };
        var complexProcessor = new ComplexProcessor(processors, doNothing);
        ObjectForMessage obj = new ObjectForMessage();
        obj.setData(Arrays.asList("field13", "inner", "data"));
        var message = new Message.Builder()
                .field12("field12")
                .field11("field11")
                .field13(obj)
                .build();

        var result = complexProcessor.handle(message);
        assertThat(result.getField11().equals(message.getField12()));
        assertThat(result.getField12().equals(message.getField11()));

    }

    @Test
    @DisplayName("Testing of throwing exception each second which is even. TODO № 3")
    void throwExceptionEachEvenSecondTest() {
        var processors = List.of(
                (Processor) new ProcessorWhichThrowsExceptionsAtEachEvenSecond(
                        new ProcessorEcho()
                )
        );

        Consumer<Exception> checkThatTimeIsEven = (Exception ex) -> {
            if (ex instanceof TimeIsEvenException) {
                TimeIsEvenException exception = (TimeIsEvenException) ex;
                assertThat(exception.getTime().getSecond() % 2 == 0);
            }
        };

        var complexProcessor = new ComplexProcessor(processors, checkThatTimeIsEven);
        ObjectForMessage obj = new ObjectForMessage();
        obj.setData(Arrays.asList("field13", "inner", "data"));
        var message = new Message.Builder().build();
        var result = complexProcessor.handle(message);
    }

    @Test
    @DisplayName("Testing of history saving. Creates file in root of project. TODO № 4")
    void saveHistoryTest() {
        var processors
                = List.of((Processor) new ProcessorEcho());
        Consumer<Exception> doNothing = (Exception ex) -> {
        };
        var complexProcessor = new ComplexProcessor(processors, doNothing);
        ObjectForMessage obj = new ObjectForMessage();
        obj.setData(Arrays.asList("field13", "inner", "data"));
        var message = new Message.Builder()
                .field12("field12")
                .field11("field11")
                .field13(obj)
                .build();

        var historian = new ListenerHistorian(Paths.get("history-script.log"));
        complexProcessor.addListener(historian);
        complexProcessor.handle(message);
        complexProcessor.removeListener(historian);
    }

}

