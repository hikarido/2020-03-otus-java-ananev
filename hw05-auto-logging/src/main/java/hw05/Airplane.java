package hw05;

import java.util.List;

public class Airplane implements Flying {

    @Override
    @AOPLogging
    public boolean startFlight(List<String> route) {
        System.out.println("start");
        return false;
    }

    @Override
    @AOPLogging
    public boolean endFlight(String reason) {
        System.out.println("end");
        return false;
    }

    @Override
    public void canBeNonLoggable(String param) {
        System.out.println("no log");
    }


}
