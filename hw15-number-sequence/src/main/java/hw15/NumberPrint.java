package hw15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Принтер последовательности числе.
 * Расчитан для вывода данных в двух потоках.
 * Для первого потока необходиом установить threadId == 1
 * для второго threadId == 2.
 * При корректной настройке первый поток будет выводить
 * первым цифру, второй вторым и увеличивать общий счетчик.
 */
class NumberPrint {
    private static final Logger logger = LoggerFactory.getLogger(NumberPrint.class.getName());
    private final int sequenceLength;
    private final int howManyTimesRepeat;
    private int endToEndCounter = 1;
    private int count = 1;
    private int modifier = 1;

    private boolean isFirstDone = false;

    NumberPrint(int sequenceLength, int howManyTimesRepeat) {
        this.howManyTimesRepeat = howManyTimesRepeat;
        this.sequenceLength = sequenceLength;
    }

    /**
     * 123456543212345654321
     * ^    ^
     * ------
     * SE
     * ^                   ^
     * ---------------------
     * REPEAT
     * SE - sequence length  = 6
     * REPEAT - how many times to repeat = 21
     *
     * @param threadId
     */
    public synchronized void print(int threadId) {
        try {
            for (int i = 0; i < howManyTimesRepeat; i++) {
                if (threadId == 1 && !isFirstDone) {
                    doFirstThreadJob(threadId);
                }

                if (threadId == 2) {
                    doSecondThreadJob(threadId);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Don't care");
        }
    }

    private void doFirstThreadJob(int threadId) throws InterruptedException {
        logger.info("{} Count: {}, thread id {}", endToEndCounter, count, threadId);
        isFirstDone = true;
        notifyAll();
        wait();
    }

    private void doSecondThreadJob(int threadId) throws InterruptedException {
        while (!isFirstDone) {
            wait();
        }
        logger.info("{} Count: {}, thread id {}\n", endToEndCounter, count, threadId);
        count += modifier;
        isFirstDone = false;

        if (isTimeToReverseSequence()) {
            modifier = -modifier;
        }

        endToEndCounter += 1;

        notifyAll();
        wait();
    }

    private boolean isTimeToReverseSequence() {
        return count == 1 || count == sequenceLength;
    }
}
