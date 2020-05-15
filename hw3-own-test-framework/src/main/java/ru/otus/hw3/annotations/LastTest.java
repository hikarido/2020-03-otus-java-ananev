package ru.otus.hw3.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Indicates that method will be executed after all tests which are marked by @Test
 * but before first @After method.
 *
 * Duplicates of annotation in same class in not permitted.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface LastTest {
}
