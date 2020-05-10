package ru.otus.hw3;

import java.lang.Class;
import java.util.logging.Logger;

/**
 * Explores one class on presence of tests
 * And executes them
 */
public class TestRunner {
    public TestRunner(String name){
        try{
            Class clazz = Class.forName(name);
        }
        catch(ClassNotFoundException e){
            Logger.getLogger(this.getClass().getCanonicalName()).severe(e.toString());
        }
    }
}
