package ru.otus.jdbc.mapper.hw9.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates template sql queries for class which is passed to constructor
 */
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    public EntityClassMetaDataImpl<T> getClassMetaData() {
        return classMetaData;
    }

    private final EntityClassMetaDataImpl<T> classMetaData;
    private final static String selectAllSqlTemplate = "SELECT * FROM %s;";
    private final static String selectByIdSqlTemplate = "SELECT * FROM %s WHERE %s = ?;";
    private final static String insertSqlTemplate = "INSERT INTO %s (%s) VALUES (%s);";
    private final static String updateSqlTemplate = "UPDATE %s SET (%s) WHERE ? = ?;";
    private final static String questionJDBCPlaceholder = "?";
    private final static String sqlFieldAndValuesSeparator = ", ";

    public EntitySQLMetaDataImpl(Class<?> clazz) {
        classMetaData = new EntityClassMetaDataImpl<>(clazz);
    }

    /**
     * Generates
     * SELECT * FROM TABLE_NAME;
     *
     * @return
     */
    @Override
    public String getSelectAllSql() {
        return String.format(selectAllSqlTemplate, classMetaData.getName());
    }

    /**
     * Generates
     * SELECT * FROM TABLE_NAME WHERE ID_FIELD_NAME = ?
     *
     * @return
     */
    @Override
    public String getSelectByIdSql() {
        return String.format(selectByIdSqlTemplate, classMetaData.getName(), classMetaData.getIdField().getName());
    }

    /**
     * Generates
     * INSERT INTO TABLE_NAME (FIELD_1, FIELD_2, ..., FIELD_N) VALUES (VALUE_1, VALUE_2, ..., VALUE_N)
     *
     * @return
     */
    @Override
    public String getInsertSql() {
        String tableName = classMetaData.getName();
        List<Field> fields = classMetaData.getFieldsWithoutId();
        List<String> columnNames = fields.stream().map(o -> o.getName()).collect(Collectors.toList());
        String columnsFiller = String.join(sqlFieldAndValuesSeparator, columnNames);
        String questions = Collections.nCopies(columnNames.size(), questionJDBCPlaceholder)
                .stream()
                .collect(Collectors.joining(sqlFieldAndValuesSeparator));

        return String.format(insertSqlTemplate, tableName, columnsFiller, questions);
    }

    /**
     * Generates
     * UPDATE TABLE_NAME SET FIELD_1 = ?, FIELD_2 = ?, ..., FIELD_N = ?
     * WHERE ? = ?
     *
     * @return
     */
    @Override
    public String getUpdateSql() {
        String tableName = classMetaData.getName();
        List<Field> fields = classMetaData.getFieldsWithoutId();
        String nameAssignQuestionPlaceholder = fields
                .stream()
                .map(o -> o.getName() + " = " + questionJDBCPlaceholder)
                .collect(Collectors.joining(sqlFieldAndValuesSeparator));

        return String.format(updateSqlTemplate, tableName, nameAssignQuestionPlaceholder);
    }
}
