package hw05.AutoLogBuilder;

import hw05.AOPLogging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Handle all methods are annotated by {@link AOPLogging}
 * trace them as:
 * [LOG] method name: [arguments]
 *
 * Sample:
 * <pre>
 * interface Movable{
 *     boolean up();
 *     boolean down();
 * }
 *
 * class Car2D implements Movable{
 *     &#64;Override
 *     &#64;AOPLogging
 *     boolean up(){} // it will log name  and args
 *
 *     &#64;Override
 *     boolean down(){} // it will not log name and args
 * }
 * </pre>
 * @param <T> class implements interface methods of which annotated by {@link AOPLogging}
 */
class LoggerInvocationHandler <T> implements InvocationHandler {
    private final T toLogObject;
    private final List<String> autoLoggedMethodsIds;
    public LoggerInvocationHandler(T markedAsAOPLogging){
        toLogObject = markedAsAOPLogging;
        autoLoggedMethodsIds = determineMethodsForAutoLogging(markedAsAOPLogging.getClass().getMethods());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(isLoggingNeed(method)){
            System.out.println("[LOG] " + method.getName() + ": " + Arrays.toString(args));
        }
        return method.invoke(toLogObject, args);
    }

    private boolean isLoggingNeed(Method method) {
        return Collections.binarySearch(autoLoggedMethodsIds, method.getName()) >= 0;
    }

    private List<String> determineMethodsForAutoLogging(Method[] methods){
        List<String> methodsToLog = new ArrayList<>();
        for(Method method: methods){
            if(method.isAnnotationPresent(AOPLogging.class)){
                methodsToLog.add(method.getName());
            }
        }
        Collections.sort(methodsToLog);
        return methodsToLog;
    }
}
