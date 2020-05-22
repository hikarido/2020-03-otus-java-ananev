package hw05;

import java.util.List;

public class Airplain implements Flying {

    @Override
    @AOPLogging
    public boolean startFlight(List<String> route) {
        return false;
    }

    @Override
    @AOPLogging
    public boolean endFlight(String reason) {
        return false;
    }

    @Override
    public void canBeNonLoggable(String param) {

    }


}
