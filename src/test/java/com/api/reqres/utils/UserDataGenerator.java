package com.api.reqres.utils;

import java.util.Random;

/**
 * ✅ Утилита для генерации случайных имён и должностей для тестов
 */
public class UserDataGenerator {

    private static final String[] NAMES = {
            "morpheus", "neo", "trinity", "oracle", "smith", "cypher", "tank", "dozer"
    };

    private static final String[] JOBS = {
            "leader", "hacker", "agent", "operator", "engineer", "analyst", "developer"
    };

    private static final Random RANDOM = new Random();

    /**
     * 🔍 Возвращает случайное имя из списка
     */
    public static String generateName() {
        return NAMES[RANDOM.nextInt(NAMES.length)];
    }

    /**
     * 🔍 Возвращает случайную должность из списка
     */
    public static String generateJob() {
        return JOBS[RANDOM.nextInt(JOBS.length)];
    }
}