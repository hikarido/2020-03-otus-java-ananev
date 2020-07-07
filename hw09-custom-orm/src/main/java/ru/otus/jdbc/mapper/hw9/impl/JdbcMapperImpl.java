package ru.otus.jdbc.mapper.hw9.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private Class<T> mappingClass;
    private EntitySQLMetaDataImpl<T> sqlGenerator;
    private EntityClassMetaDataImpl<T> metaData;
    private DbExecutor<T> sqlExecutor;

    private static Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    // TODO по идее ту должен быть ru.otus.core.sessionmanager.SessionManager
    // Но в его getSession вернётся ru.otus.core.sessionmanager.DatabaseSession
    // Этот DatabaseSession пустой и получить сессию для DbExecutor нет возможности
    SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(Class<T> clazz, SessionManagerJdbc manager) {
        mappingClass = clazz;
        sessionManager = manager;
        sqlGenerator = new EntitySQLMetaDataImpl<T>(clazz);
        metaData = sqlGenerator.getClassMetaData();
        sqlExecutor = new DbExecutorImpl<T>();
    }

    @Override
    public void insert(T objectData) {
        String insertSql = sqlGenerator.getInsertSql();
        try {
            sqlExecutor.executeInsert(
                    sessionManager.getCurrentSession().getConnection(),
                    insertSql,
                    getFieldValuesWithoutIdOf(objectData)
            );
            sessionManager.commitSession();
        } catch (SQLException throwables) {
            throw new CantExecuteSqlStatement(throwables);
        }


    }

    @Override
    public void update(T objectData) {
        String updateSql = sqlGenerator.getUpdateSql();
        List<Object> params = getFieldValuesWithoutIdOf(objectData);
        Field idField = metaData.getIdField();
        String idColumnName = idField.getName();
        try {
            Object idColumnValue = idField.get(objectData);
            params.add(idColumnValue);

            sqlExecutor.executeInsert(
                    sessionManager.getCurrentSession().getConnection(),
                    updateSql,
                    params
            );

            sessionManager.commitSession();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            var exc = new CantGetValueOfField();
            exc.addSuppressed(e);
            throw exc;
        } catch (SQLException e) {
            throw new CantExecuteSqlStatement(e);
        }


    }

    @Override
    public void insertOrUpdate(T objectData) {
        Field idField = metaData.getIdField();
        try {
            long idValue = (long) idField.get(objectData);
            boolean isIdPresenceInDatabase = findById(idValue, (Class<T>) objectData.getClass()) != null;
            if (isIdPresenceInDatabase) {
                update(objectData);
            } else {
                insert(objectData);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            var exc = new CantGetValueOfField();
            exc.addSuppressed(e);
            throw exc;
        }
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        String selectSql = sqlGenerator.getSelectByIdSql();

        Function<ResultSet, T> resultHandler = (rs) -> {
            try {
                if (rs.next()) {
                    return createObjectFromResultSet(rs);
                } else {
                    return null;
                }
            } catch (SQLException throwables) {
                throw new CantExecuteSqlStatement(throwables);
            }
        };

        try {
            Optional<T> result = sqlExecutor.executeSelect(
                    sessionManager.getCurrentSession().getConnection(),
                    selectSql,
                    id,
                    resultHandler
            );

            return result.orElse(null);
        } catch (SQLException throwables) {
            sessionManager.rollbackSession();
            throw new CantExecuteSqlStatement(throwables);
        }
    }

    private List<Object> getFieldValuesWithoutIdOf(T objectData) {
        List<Field> fields = metaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>(fields.size());
        for (Field field : fields) {
            try {
                params.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                var exc = new CantGetValueOfField();
                exc.addSuppressed(e);
                throw exc;
            }
        }

        return params;
    }

    private T createObjectFromResultSet(ResultSet resultSet) {
        Constructor<T> constructor = metaData.getConstructor();
        List<Field> fields = metaData.getAllFields();
        try {
            T obj = constructor.newInstance();
            for (Field field : fields) {
                field.set(obj, resultSet.getObject(field.getName()));
            }

            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CantInstantiateNewObject(e);
        } catch (SQLException throwables) {
            throw new CantInstantiateNewObject(throwables);
        }
    }
}