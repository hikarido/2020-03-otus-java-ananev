package ru.otus.jdbc.mapper.hw9.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;


/**
 * Tokenize class into name, default public constructor, fields, field with {@link Id} annotation
 *
 * @param <T>
 */
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private static Class<?> clazz;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        EntityClassMetaDataImpl.clazz = clazz;
        getIdField();
    }

    @Override
    public String getName() {
        return clazz.getName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructor() {
        try {
            return (Constructor<T>) clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new HaveNoPublicConstructor();
        }
    }

    @Override
    public Field getIdField() {
        Field[] fields = clazz.getFields();
        List<Field> idAnnotated = new LinkedList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idAnnotated.add(field);
            }
        }

        if (idAnnotated.size() > 1) {
            throw new IdFieldMustBeOnlyOne();
        }

        if (idAnnotated.isEmpty()) {
            throw new HaveNoIdField();
        }

        return idAnnotated.get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(clazz.getFields()).collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(clazz.getFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }
}
