package manager;

import annotations.OrmColumn;
import annotations.OrmColumnId;
import annotations.OrmEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrmManager {
    private final HikariDataSource ds = getDataSource();
    private final Map<String, Class<?>> classMap;

    public OrmManager() {
        classMap = getClassOrmEntity();
    }

    private HikariDataSource getDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/day07.ex02");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setValidationTimeout(300_000);
        return new HikariDataSource(config);
    }

    public Map<String, Class<?>> getClassOrmEntity() {
        Map<String, Class<?>> classes = new HashMap<>();
        try {
            String packageName = "model";
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    for (File file : Objects.requireNonNull(directory.listFiles())) {
                        if (file.getName().endsWith(".class")) {
                            String className = file.getName().substring(0, file.getName().length() - 6);
                            String fullClassName = packageName + "." + className;
                            Class<?> clazz = Class.forName(fullClassName);
                            if (clazz.isAnnotationPresent(OrmEntity.class)) {
                                classes.put(className, clazz);
                            }
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void initialize() {
        for (String className : classMap.keySet()) {
            Class<?> clazz = classMap.get(className);
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            StringBuilder query = new StringBuilder("DROP TABLE IF EXISTS ")
                    .append(tableName)
                    .append(";\n")
                    .append("CREATE TABLE IF NOT EXISTS ")
                    .append(tableName)
                    .append(" (");
            Field[] fields = clazz.getDeclaredFields();
            boolean first = true;
            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    if (first) {
                        first = false;
                    } else query.append(",");
                    String fieldType = field.getType().getSimpleName();
                    if (fieldType.equals("long") || fieldType.equals("Long")) {
                        query.append("id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY");
                    } else {
                        throw new RuntimeException("Type must be long");
                    }
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    if (first) {
                        first = false;
                    } else query.append(",");
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    String fieldType = field.getType().getSimpleName();
                    String columnName = ormColumn.name();
                    int columnLength = ormColumn.length();
                    query.append(columnName).append(" ");
                    switch (fieldType) {
                        case "String":
                            query.append("varchar(").append(columnLength).append(")");
                            break;
                        case "Integer":
                            query.append("int");
                            break;
                        case "Long":
                            query.append("bigint");
                            break;
                        case "Double":
                            query.append("double");
                            break;
                        case "Boolean":
                            query.append("boolean");
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
                    }
                }
            }
            query.append(");");
            System.out.println("SQL Query:");
            updateQuery(query.toString());
        }
    }

    private void updateQuery(String sql){
        try {
            printQuery(sql);
            ds.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printQuery(String query) {
        System.out.println(query);
    }

    public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    columns.add("id");
                    values.add(field.get(entity));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    columns.add(ormColumn.name());
                    values.add(field.get(entity));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        StringBuilder query = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (");
        for (int i = 0; i < columns.size(); i++) {
            query.append(columns.get(i));
            if (i + 1 != columns.size()) {
                query.append(",");
            } else query.append(")");
        }
        query.append(" values (");
        for (int i = 0; i < values.size(); i++) {
            Object objValue = values.get(i);
            if (objValue.getClass().getSimpleName().equals("String")) {
                query.append("'").append(objValue).append("'");
            } else {
                query.append(objValue);
            }
            if (i + 1 != values.size()) {
                query.append(",");
            } else query.append(");");
        }
        System.out.println("SQL query for save:");
        updateQuery(query.toString());
    }

    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        long id = 0;
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    id = (long) field.get(entity);
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    columns.add(ormColumn.name());
                    values.add(field.get(entity));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        if (id == 0) {
            return;
        }
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(tableName)
                .append(" SET ");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            Object value = values.get(i);
            query.append(column).append("=");
            if (value.getClass().getSimpleName().equals("String")) {
                query.append("'").append(value).append("'");
            } else {
                query.append(value);
            }
            if (i + 1 != columns.size()) {
                query.append(",");
            }
        }
        query.append(" WHERE id=").append(id).append(";");
        System.out.println("SQL query for update:");
        updateQuery(query.toString());
    }

    public <T> T findById(Long id, Class<T> aClass){
        if (!aClass.isAnnotationPresent(OrmEntity.class)) return null;

        String tableName = aClass.getAnnotation(OrmEntity.class).table();
        ResultSet resultSet = executeQuery(
                "select * from " +
                        tableName +
                        " where id=" +
                        id +
                        ";"
        );

        try {
            if (!resultSet.next()) return null;
            T newInstance = aClass.newInstance();
            for (Field field : aClass.getDeclaredFields()){
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)){
                    field.set(newInstance, resultSet.getLong("id"));
                } else if (field.isAnnotationPresent(OrmColumn.class)){
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    field.set(newInstance, resultSet.getObject(ormColumn.name()));
                }
                field.setAccessible(false);
            }
            return newInstance;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private ResultSet executeQuery(String query) {
        try {
            printQuery(query);
            return ds.getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        ds.close();
    }
}
