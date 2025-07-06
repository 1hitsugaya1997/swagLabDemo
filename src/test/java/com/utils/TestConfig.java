package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл test.properties не найден в ресурсах");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки test.properties", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getLogin(String userKey) {
        return props.getProperty("login." + userKey + ".username", "");
    }

    public static String getPassword(String userKey) {
        return props.getProperty("login." + userKey + ".password", "");
    }

    public static String getBaseUrl() {
        return props.getProperty("url.base");
    }

    public static String getReqresBaseUrl() {
        return props.getProperty("reqres.url.base");
    }
}
