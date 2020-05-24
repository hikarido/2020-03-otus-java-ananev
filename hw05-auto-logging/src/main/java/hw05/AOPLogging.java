package hw05;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * It intended to inject auto logging aspect.
 * If method was marked by this annotation
 * it can be used in auto loggable proxy.
 *
 * Handlers
 * {@link hw05.AutoLogBuilder.AOPLoggingBuilder}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AOPLogging {
}
