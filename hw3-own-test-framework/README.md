# Домашнее задание 3
## Свой тестовый фреймворк
### Цель
* научиться работать с reflection и аннотациями, понять принцип работы фреймворка junit.
* написать свой тестовый фреймворк.

## Framework
* Поддержать свои аннотации @Test, @Before, @After.
* Запускать вызовом статического метода с именем класса с тестами.

## Details

Т.е. надо сделать:
1) создать три аннотации - @Test, @Before, @After.
2) Создать класс-тест, в котором будут методы, отмеченные аннотациями.
3) Создать "запускалку теста". На вход она должна получать имя класса с тестами, в котором следует найти и запустить методы отмеченные аннотациями и пункта 1.
4) Алгоритм запуска должен быть следующий::
метод(ы) Before
текущий метод Test
метод(ы) After
для каждой такой "тройки" надо создать СВОЙ объект класса-теста.
5) Исключение в одном тесте не должно прерывать весь процесс тестирования.
6) На основании возникших во время тестирования исключений вывести статистику выполнения тестов (сколько прошло успешно, сколько упало, сколько было всего) 

## Исполнение
* TestRunnerTest.runBoringRocketScienceBusinessLogicTest - тест для запуска тестирующего фреймворка

## Framework
* ru.otus.hw3.assertions.Assertions - предосставляет assert методы
* ru.otus.hw3.annotations - предоставляет аннотации
    * Before - выполняется перед кадлым тестом
    * After - выполняется после каждого теста
    * Test - расматривается как тест. Если один из ru.otus.hw3.assertions.Assertions бросит исключение тест помечается как FAIL, иначе SUCCESS
    * LastTest - тоже что и Test, но выполнчется после всех Test, и перед первый After

* Вывод при запуске тестового класса
```
May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner loadClassDescriptorByName
INFO: Class BoringRocketScienceBusinessLogicTest was loaded
May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: before
Method: initTestCount [SUCCESS]
Method: loadDataset [SUCCESS]
Method: preprocessDataSet [SUCCESS]
Method: loadNetwork [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: trainNetworkInRightEnvironment
Method: trainNetworkInRightEnvironment [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: after
Method: releaseDataset [SUCCESS]
Method: releaseNetworkResources [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: before
Method: initTestCount [SUCCESS]
Method: loadDataset [SUCCESS]
Method: preprocessDataSet [SUCCESS]
Method: loadNetwork [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: trainNetworkBadEnvironment
Method: trainNetworkBadEnvironment [FAIL] cause ru.otus.hw3.assertions.AssertionBaseException: Must be true

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: after
Method: releaseDataset [SUCCESS]
Method: releaseNetworkResources [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: before
Method: initTestCount [SUCCESS]
Method: loadDataset [SUCCESS]
Method: preprocessDataSet [SUCCESS]
Method: loadNetwork [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: synteticFailTest
Method: synteticFailTest [FAIL] cause ru.otus.hw3.assertions.AssertionBaseException: Must be true

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: after
Method: releaseDataset [SUCCESS]
Method: releaseNetworkResources [SUCCESS]

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner runOrderedTestSequence
INFO: 
CATEGORY: last
Method: countMustBeOne [FAIL] cause ru.otus.hw3.assertions.AssertionBaseException: Must be true

May 12, 2020 4:12:54 PM ru.otus.hw3.TestRunner displaySummary
INFO: 
Summary
all: 3, success: 1 fail: 2
```