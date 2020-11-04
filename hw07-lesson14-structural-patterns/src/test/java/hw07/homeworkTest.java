package hw07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.Message;
import ru.otus.ObjectForMessage;
import ru.otus.handler.ComplexProcessor;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorSwapFields11And12;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class homeworkTest {
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
}
