package ru.otus.jdbc.mapper.hw9.impl;

/**
 * Root exception for hw09
 */
public class HomeworkException extends RuntimeException{
    public HomeworkException(String msg){
        super(msg);
    }

    public HomeworkException(){
        super();
    }

    public HomeworkException(Exception e){
        super(e);
    }
}
