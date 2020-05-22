package hw05;

import java.util.List;

public interface Flying {
    boolean startFlight(List<String> route);
    boolean endFlight(String reason);
    void canBeNonLoggable(String param);
}
