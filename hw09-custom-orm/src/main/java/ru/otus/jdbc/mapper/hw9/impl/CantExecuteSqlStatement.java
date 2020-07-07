package ru.otus.jdbc.mapper.hw9.impl;

public class CantExecuteSqlStatement extends HomeworkException{
    public CantExecuteSqlStatement(Exception e){
        super(e);
    }
}
