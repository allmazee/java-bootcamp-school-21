package edu.school21.orm.ormmanager;


import edu.school21.orm.annotations.OrmColumn;
import edu.school21.orm.annotations.OrmColumnId;
import edu.school21.orm.annotations.OrmEntity;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import static org.reflections.scanners.Scanners.*;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class OrmManager {
    private DataSource dataSource;
    private Reflections reflections;
    private Set<Class<?>> annotatedClasses;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage("edu.school21.orm.models")
                        .setScanners(TypesAnnotated, FieldsAnnotated));
    }

    public void init() {
        annotatedClasses = getAnnotatedClasses();
        dropTables();
        createTables();
    }

    public Set<Class<?>> getAnnotatedClasses() {
        return reflections.get(SubTypes.of(
                TypesAnnotated.with(OrmEntity.class)).asClass());
    }

    private void dropTables() {
        StringBuilder dropQuery = new StringBuilder("DROP TABLE IF EXISTS ");
        for (Class<?> aClass : annotatedClasses) {
            OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
            dropQuery.append(ormEntity.table()).append(";");
        }
        executeUpdateQuery(dropQuery.toString());
        printQuery(dropQuery.toString());
    }

    private void createTables() {
        String createStr = "CREATE TABLE IF NOT EXISTS ";
        for (Class<?> aClass : annotatedClasses) {
            OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
            StringBuilder createQuery = new StringBuilder();
            createQuery.append(createStr)
                    .append(ormEntity.table())
                    .append(" (\n");

            List<Field> columns = new ArrayList<>();
            Field columnId = null;

            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    columnId = field;
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    columns.add(field);
                }
            }

            if (columnId != null) {
                createQuery.append(columnId.getName())
                        .append(" SERIAL PRIMARY KEY,\n");
            }

            for (Field column : columns) {
                OrmColumn ormColumn = column.getAnnotation(OrmColumn.class);
                createQuery.append(ormColumn.name());
                if (column.getType().equals(String.class)) {
                    createQuery.append(" VARCHAR(")
                            .append(ormColumn.length())
                            .append("),\n");
                } else if (column.getType().equals(Integer.class)) {
                    createQuery.append(" INT,\n");
                } else if (column.getType().equals(Double.class)) {
                    createQuery.append(" DOUBLE,\n");
                } else if (column.getType().equals(Long.class)) {
                    createQuery.append(" BIGINT,\n");
                } else if (column.getType().equals(Boolean.class)) {
                    createQuery.append(" BOOLEAN,\n");
                }
            }

            createQuery.delete(createQuery.length() - 2,
                    createQuery.length() - 1);
            createQuery.append(");");
            executeUpdateQuery(createQuery.toString());
            printQuery(createQuery.toString());
        }
    }

    public void save(Object entity) {
        Class<?> aClass = entity.getClass();
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            throw new IllegalArgumentException(
                    aClass.getName() + " is not annotated with @OrmEntity");
        }
        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        StringBuilder insertQuery = new StringBuilder(
                "INSERT INTO " + ormEntity.table() + " (");
        int valuesNumber = 0;
        List<Object> columnValues = new ArrayList<>();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumn.class)) {
                String columnName = field.getAnnotation(OrmColumn.class).name();
                insertQuery.append(columnName).append(", ");
                try {
                    columnValues.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                valuesNumber++;
            }
        }
        insertQuery.delete(insertQuery.length() - 2, insertQuery.length());
        insertQuery.append(") VALUES (");
        for (int i = 0; i < valuesNumber; i++) {
            insertQuery.append("?, ");
        }
        insertQuery.delete(insertQuery.length() - 2, insertQuery.length());
        insertQuery.append(");");
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(insertQuery.toString());
            for (int i = 0; i < valuesNumber; i++) {
                statement.setObject(i + 1, columnValues.get(i));
            }
            statement.executeUpdate();
            printQuery(insertQuery.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Object entity) {
        Class<?> aClass = entity.getClass();
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            throw new IllegalArgumentException(
                    aClass.getName() + " is not annotated with @OrmEntity");
        }
        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        StringBuilder updateQuery = new StringBuilder(
                "UPDATE " + ormEntity.table() + " SET ");
        List<Object> columnValues = new ArrayList<>();
        long idValue = 0;
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumn.class)) {
                String columnName = field.getAnnotation(OrmColumn.class).name();
                updateQuery.append(columnName).append(" = ?, ");
                try {
                    columnValues.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                try {
                    idValue = (long) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        updateQuery.deleteCharAt(updateQuery.length() - 2);
        updateQuery.append("WHERE id = ?;");
        printQuery(updateQuery.toString());
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(updateQuery.toString());
            for (int i = 0; i < columnValues.size(); i++) {
                statement.setObject(i+ 1, columnValues.get(i));
            }
            statement.setLong(columnValues.size() + 1, idValue);
            statement.executeUpdate();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public <T> T findById(Long id, Class<T> aClass) {
        T instance = null;
        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        String columnIdName = "id";
        for (Field field : aClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                columnIdName = field.getName();
                break;
            }
        }
        String selectQuery = "SELECT * FROM " + tableName
                + " WHERE " + columnIdName + " = " + id + ";";
        printQuery(selectQuery);
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectQuery);
            if (rs.next()) {
                instance = aClass.newInstance();
                for (Field field : aClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(OrmColumnId.class)) {
                        field.set(instance, rs.getLong(columnIdName));
                    } else if (field.isAnnotationPresent(OrmColumn.class)) {
                        String columnName
                                = field.getAnnotation(OrmColumn.class).name();
                        field.set(instance,
                                rs.getObject(columnName, field.getType()));
                    }
                }
            }
        } catch (SQLException | IllegalAccessException
                 | InstantiationException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void executeUpdateQuery(String sql) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printQuery(String sql) {
        System.out.println("Generated SQL query:\n" + sql + "\n");
    }

}
