package ru.otus.jdbc.mapper.hw9.impl;

/**
 * By contract of {@link ru.otus.jdbc.mapper.EntityClassMetaData#getIdField()}
 * Class for mapping must has one field which is annotated by {@link Id}
 * If this contract was violated this exception must be thrown
 */
public class HaveNoIdField extends HomeworkException{
}
