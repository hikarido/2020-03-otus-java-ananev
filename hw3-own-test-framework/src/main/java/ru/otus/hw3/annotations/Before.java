package ru.otus.hw3.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Indicates that method is intended to be launched before method which annotated by @Test
 * If class as more than one methods which are marked by @Before
 * then order of execution of "before" methods is not defined
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Before {
}
