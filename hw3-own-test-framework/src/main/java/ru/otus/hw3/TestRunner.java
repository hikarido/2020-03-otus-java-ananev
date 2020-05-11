package ru.otus.hw3;

import com.sun.jdi.InvocationException;

import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Explores one class on presence of tests
 * And executes them
 */
public class TestRunner {

    private final Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    public TestRunner(String name){
        Class<?> inspectingClazz = loadClassDescriptorByName(name);
        ArrayList<Method> testMethods = new ArrayList<>();
        ArrayList<Method> beforeMethods = new ArrayList<>();
        ArrayList<Method> afterMethods = new ArrayList<>();

        if(inspectingClazz != null){
            Object test = instantiateClass(inspectingClazz);

        }
    }

    private Object instantiateClass(Class<?> inspectingClazz) {
        Constructor<?> constructor = getDefaultConstructor(inspectingClazz);
        try {
            Object test = constructor.newInstance();
            return test;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.SEVERE, e.toString());
            throw  new RuntimeException("Can't instantiate class", e);
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























