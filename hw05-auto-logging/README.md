
# Домашнее задание 5
## Автоматическое логирование
# Цель
* Понять как реализуется AOP
* Какие для этого есть технические средства

# Разработайте функционал
* метод класса можно пометить самодельной аннотацией @Log, например, так:
```
class TestLogging {
    @Log
    public void calculation(int param) {};
}
```

* При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.

```
class Demo {
    public void action() {
        new TestLogging().calculation(6);
    }
}
```

* В консоле дожно быть:
```
executed method: calculation, param: 6
```
* Возможные реализации
    * [ASM](https://asm.ow2.io/)
    * [java.lang.reflect.Proxy](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)