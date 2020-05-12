package ru.otus.hw3;

import java.lang.Class;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import ru.otus.hw3.annotations.*;
import ru.otus.hw3.assertions.AssertionBaseException;

/**
 * Explores one class on presence of tests
 * And executes them
 */
public class TestRunner {

    private final Logger logger;

    public TestRunner(String name){
        logger = Logger.getLogger(this.getClass().getCanonicalName());
        Class<?> inspectingClazz = loadClassDescriptorByName(name);
        List<Method> testMethods = new ArrayList<>();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> lastTest = new ArrayList<>();

        if(inspectingClazz != null){
            for(Method method: inspectingClazz.getDeclaredMethods()) {
                if(method.getModifiers() == Modifier.PUBLIC) {
                    arrangeMethodsByAnnotationType(beforeMethods, testMethods, afterMethods, lastTest, method);
                }
            }

            Object test = instantiateClass(inspectingClazz);
            runOrderedTestSequence(test, beforeMethods, testMethods, afterMethods, lastTest);
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

    private void arrangeMethodsByAnnotationType(
            List<Method> before,
            List<Method> test,
            List<Method> after,
            List<Method> lastTest,
            Method method) {

// I thought do this way but it looks like wild crutch
// TODO: what can we do in this case?
//
//        Annotation[] annotations = method.getDeclaredAnnotations();
//        if(annotations.length != 0){
//            for(Annotation annotation: annotations){
//                if(annotation.getClass().equals(Before.class.toString())){
//
//                }
//            }
//        }

        if(method.getAnnotation(Before.class) != null){
            before.add(method);
        }else if(method.getAnnotation(Test.class) != null){
            test.add(method);
        }else if(method.getAnnotation(After.class) != null){
            after.add(method);
        } else if(method.getAnnotation(LastTest.class) != null){
            lastTest.add(method);
        }
    }

    private void runOrderedTestSequence(
            Object instance,
            List<Method> beforeMethods,
            List<Method> testMethods,
            List<Method> afterMethods,
            List<Method> lastTestMethods
    ){
        Map<String, List<Method>> categories = new LinkedHashMap<>(4);
        categories.put("before", beforeMethods);
        categories.put("test", testMethods);
        categories.put("after", afterMethods);
        categories.put("last", lastTestMethods);

        Statistic beforeStatistic;
        Statistic testStatistic;
        Statistic afterStatistic;
        Statistic lastStatistic;

        try {
            String logFtm = "\n%s";
            for(Method method: categories.get("test")){
                testStatistic = new Statistic(method.getName());
                beforeStatistic = invokeCategory("before", categories.get("before"), instance);
                invokeOne(method, instance, testStatistic);
                afterStatistic = invokeCategory("after", categories.get("after"), instance);

                logger.log(Level.INFO, String.format(logFtm, beforeStatistic.toString()));
                logger.log(Level.INFO, String.format(logFtm, testStatistic.toString()));
                logger.log(Level.INFO, String.format(logFtm, afterStatistic.toString()));
            }

            lastStatistic = invokeCategory("last", categories.get("last"), instance);
            logger.log(Level.INFO, String.format(logFtm, lastStatistic.toString()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't run method: ", e);
        }
    }

    private Statistic invokeCategory(String category, List<Method> methods, Object instance)
            throws IllegalAccessException, InvocationTargetException{
        Statistic stats = new Statistic(category);

        for(Method method: methods){
            invokeOne(method, instance, stats);
        }

        return stats;
    }

    private void invokeOne(Method method, Object instance, Statistic statistic)
            throws  InvocationTargetException , IllegalAccessException{
        try{
            method.invoke(instance);
            statistic.addSuccess(method.getName());
        }
        catch (InvocationTargetException e){
            if(e.getCause() instanceof AssertionBaseException){
                statistic.addFault(method.getName(), e.getCause());
            }else {
                throw e;
            }
        }
    }
}





















