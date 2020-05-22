package hw05;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class AOPLoggingBuilder{
    private AOPLoggingBuilder(){

    }

    public static <Marked, Interface>
    Interface build(Marked markedAsAutoLoggable,Class interfaceToLog){
        var handler = new LoggerInvocationHandler(markedAsAutoLoggable);
        return (Interface) Proxy.newProxyInstance(
                AOPLoggingBuilder.class.getClassLoader(),
                new Class[]{interfaceToLog},
                handler
        );
    }

    private static class LoggerInvocationHandler <T> implements InvocationHandler{
        private T toLogObject;
        public LoggerInvocationHandler(T markedAsAOPLogging){
            toLogObject = markedAsAOPLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getAnnotation(AOPLogging.class) != null){
                System.out.println(method.getName());
            }
//            if(method.isAnnotationPresent(AOPLogging.class)){
                System.out.println(method.getName() + ": " + Arrays.toString(args));
//            }
            return method.invoke(toLogObject, args);
        }
    }
}
