package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final Properties testProps = new Properties();
    private static final Properties junitProps = new Properties();

    static {
        loadProperties("test.properties", testProps);
        loadProperties("junit-platform.properties", junitProps);
    }

    private static void loadProperties(String fileName, Properties target) {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Файл " + fileName + " не найден в ресурсах");
            }
            target.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки " + fileName, e);
        }
    }

    // 🔍 Методы для test.properties
    public static String getProperty(String key) {
        return testProps.getProperty(key);
    }

    public static String getLogin(String userKey) {
        return testProps.getProperty("login." + userKey + ".username", "");
    }

    public static String getPassword(String userKey) {
        return testProps.getProperty("login." + userKey + ".password", "");
    }

    public static String getBaseUrl() {
        return testProps.getProperty("url.base");
    }

    public static String getReqresBaseUrl() {
        return testProps.getProperty("reqres.url.base");
    }

    public static String getDbUrl() {
        return testProps.getProperty("db.url");
    }

    public static String getDbUser() {
        return testProps.getProperty("db.user");
    }

    public static String getDbPassword() {
        return testProps.getProperty("db.password");
    }

    // ✅ Методы для junit-platform.properties
    public static String getJunitProperty(String key) {
        return junitProps.getProperty(key);
    }

    public static int getJunitFixedParallelism() {
        return Integer.parseInt(junitProps.getProperty("junit.jupiter.execution.parallel.config.fixed.parallelism"));
    }

}
