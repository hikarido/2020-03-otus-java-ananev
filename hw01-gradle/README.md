# Домашнее задание 01
Проект gradle с модульной структурой
Цель: нучиться создавать проект Gradle (Maven), подготовиться к выполнению домашних заданий.
1) Создайте аккаунт на github.com (если еще нет)
2) Создайте репозиторий для домашних работ
3) Сделайте checkout репозитория на свой компьютер
4) Создайте локальный бранч hw01-gradle
5) Создать проект gradle
6) В проект добавьте последнюю версию зависимости
<groupId>com.google.guava</groupId>
<artifactId>guava</artifactId>
7) Создайте модуль hw01-gradle
8) В модуле сделайте класс HelloOtus
9) В этом классе сделайте вызов какого-нибудь метода из guava
10) Создайте "толстый-jar"
11) Убедитесь, что "толстый-jar" запускается.
12) Сделайте pull-request в gitHub
13) Ссылку на PR отправьте на проверку (личный кабинет, чат с преподавателем).

---

# Толстый jar запускается
```
hikarido@pechka:~/Forge/2020-03-otus-java-ananev$ ll
total 68
drwxr-xr-x  9 hikarido hikarido 4096 Mar 30 03:14 ./
drwxr-xr-x 34 hikarido hikarido 4096 Mar 29 23:05 ../
drwxr-xr-x 10 hikarido hikarido 4096 Mar 30 00:16 build/
-rw-r--r--  1 hikarido hikarido  668 Mar 30 02:40 build.gradle
drwxrwxr-x  5 hikarido hikarido 4096 Mar 30 01:49 buildSrc/
drwxr-xr-x  8 hikarido hikarido 4096 Mar 30 03:17 .git/
-rw-r--r--  1 hikarido hikarido  382 Mar 30 00:22 .gitignore
drwxr-xr-x  3 hikarido hikarido 4096 Mar 30 00:14 gradle/
drwxr-xr-x  6 hikarido hikarido 4096 Mar 30 00:14 .gradle/
-rwxr-xr-x  1 hikarido hikarido 5764 Mar 30 00:14 gradlew*
-rw-r--r--  1 hikarido hikarido 3056 Mar 30 00:14 gradlew.bat
drwxrwxr-x  4 hikarido hikarido 4096 Mar 30 03:00 hw01-gradle/
drwxrwxr-x  3 hikarido hikarido 4096 Mar 30 02:56 .idea/
-rw-rw-r--  1 hikarido hikarido 1075 Mar 30 01:42 LICENSE
-rw-r--r--  1 hikarido hikarido  263 Mar 30 02:53 README.md
-rw-r--r--  1 hikarido hikarido  393 Mar 30 00:55 settings.gradle
hikarido@pechka:~/Forge/2020-03-otus-java-ananev$ ./gradlew build

BUILD SUCCESSFUL in 1s
11 actionable tasks: 11 up-to-date
hikarido@pechka:~/Forge/2020-03-otus-java-ananev$ $JAVA_HOME/bin/java -jar hw01-gradle/build/libs/gradleHelloWorld-0.1.jar 
[H, e, l, l, o,  , O, t, u, s,  , 2, 0, 2, 0, .]
hikarido@pechka:~/Forge/2020-03-otus-java-ananev$

```
