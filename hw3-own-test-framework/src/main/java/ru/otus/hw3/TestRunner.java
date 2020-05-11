package ru.otus.hw3;

import java.lang.Class;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Constructor;

/**
 * Explores one class on presence of tests
 * And executes them
 */
public class TestRunner {

    private final Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    public TestRunner(String name){
        Class<?> inspectingClazz = loadClassDescriptorByName(name);
        if(inspectingClazz != null){
            Constructor<?> constructor = getDefaultConstructor(inspectingClazz);
        }
    }

    private Class<?> loadClassDescriptorByName(String name){
        try{
            Class<?> clazz = Class.forName(name);
            logger.log(Level.INFO, "Class " + clazz.getSimpleName() + " was loaded");
            return clazz;
        }
        catch(ClassNotFoundException e){
            logger.log(Level.INFO, e.toString());
            return null;
        }
    }

    private <T> Constructor<T> getDefaultConstructor(Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor;
        } catch (NoSuchMethodException e) {
            RuntimeException haveNoDefaultConstructor;
            haveNoDefaultConstructor = new RuntimeException(
                    "Class " + clazz.getSimpleName() + " have no default constructor",
                    e
            );
            throw haveNoDefaultConstructor;
        }
    }
}























