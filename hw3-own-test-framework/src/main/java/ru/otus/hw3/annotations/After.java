package ru.otus.hw3.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


/**
 * Indicates that method is intended to be launched after method witch was marked by @Test.
 * If class has more then one methods are marked by @After then execution order of "after" methods
 * is undefined
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface After {
}
