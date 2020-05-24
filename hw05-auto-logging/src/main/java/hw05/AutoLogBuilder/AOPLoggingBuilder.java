package hw05.AutoLogBuilder;

import java.lang.reflect.Proxy;

 /**
  * Allow creation of proxy which performs auto logging of name and args of invoked methods
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
  *     //.. rest of impl
  * }
  *
  * main(){
  *     Car2D car = new Car2D();
  *     Movable proxy = AOPLoggingBuilder.build(car, Movable.class)
  *     proxy.up();
  *     // yields in console
  *     // [LOG] up []
  *
  *     //but car leave all in silence
  *     car.up()
  *     // silence...
  * }
  * </pre>
  */
public class AOPLoggingBuilder{
    private AOPLoggingBuilder(){

    }

    public static <Marked, Interface>
    Interface build(Marked markedAsAutoLoggable,Class<?> interfaceToLog){
        var handler = new LoggerInvocationHandler(markedAsAutoLoggable);
        return (Interface) Proxy.newProxyInstance(
                AOPLoggingBuilder.class.getClassLoader(),
                new Class<?>[]{interfaceToLog},
                handler
        );
    }
}
