package com.api.reqres.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class TimeAssertions {

    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_INSTANT,                            // 2025-07-09T08:03:47.522Z
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,                   // 2025-07-09T08:03:47+00:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),       // 2025-07-09 08:03:47
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),       // 09-07-2025 08:03:47
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),       // 2025/07/09 08:03:47
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")        // 09/07/2025 08:03:47
    );

    /**
     * Универсальный метод проверки времени, что actualTimeIso8601 близко к текущему времени.
     * Поддерживает несколько популярных форматов.
     *
     * @param actualTimeString время в виде строки
     * @param allowedSeconds   допустимая дельта в секундах
     * @throws AssertionError если парсинг не удался или время далеко от now
     */
    public static void assertTimeCloseToNow(String actualTimeString, long allowedSeconds) {
        Instant actualInstant = parseStringToInstant(actualTimeString);

        Instant now = Instant.now();
        Duration diff = Duration.between(actualInstant, now).abs();

        if (diff.getSeconds() > allowedSeconds) {
            throw new AssertionError(String.format(
                    "Время '%s' отличается от текущего времени '%s' более чем на %d секунд",
                    actualTimeString, now, allowedSeconds));
        }
    }

    /**
     * Парсит строку времени в Instant, перебирая несколько форматов.
     *
     * @param timeString строка времени
     * @return Instant
     * @throws AssertionError если формат не распознан
     */
    private static Instant parseStringToInstant(String timeString) {
        // сначала пробуем ISO_INSTANT (Instant.parse)
        try {
            return Instant.parse(timeString);
        } catch (DateTimeParseException ignored) {
        }

        // Перебираем остальные форматы
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                // Парсим как OffsetDateTime (если есть смещение)
                OffsetDateTime odt = OffsetDateTime.parse(timeString, formatter);
                return odt.toInstant();
            } catch (DateTimeParseException ignored) {
            }

            try {
                // Парсим как LocalDateTime (без смещения)
                LocalDateTime ldt = LocalDateTime.parse(timeString, formatter);
                return ldt.toInstant(ZoneOffset.UTC);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Если ничего не подошло — кидаем ошибку
        throw new AssertionError("Не удалось распарсить время: '" + timeString + "'");
    }
}
