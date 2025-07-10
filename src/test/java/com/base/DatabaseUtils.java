package com.base;

import com.utils.TestConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    // ❗ Используем TestConfig
    private static final String DB_URL = TestConfig.getDbUrl();
    private static final String DB_USER = TestConfig.getDbUser();
    private static final String DB_PASSWORD = TestConfig.getDbPassword();

    // 🔍 Универсальный метод SELECT-запроса с возвратом результата
    public static List<String> getSingleColumnValues(String query, String columnName) {
        List<String> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                results.add(resultSet.getString(columnName));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса к БД: " + query, e);
        }

        return results;
    }

    // ✅ Для insert/update/delete
    public static int executeUpdate(String sql) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении update/insert/delete: " + sql, e);
        }
    }

    // ✅ Для получения одного значения
    public static String getSingleValue(String query, String columnName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getString(columnName);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса: " + query, e);
        }
    }
}
