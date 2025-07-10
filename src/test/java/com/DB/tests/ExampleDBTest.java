package com.DB.tests;

import com.base.DatabaseUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("База данных")
@Feature("Чтение из таблицы company")
@Story("Получение списка компаний")
@Severity(SeverityLevel.NORMAL)
public class ExampleDBTest {

    @Test
    @DisplayName("Проверка, что таблица company содержит данные")
    @Description("Выполняет SELECT * FROM company и проверяет, что хотя бы одна запись найдена")
    void shouldReturnCompaniesFromTable() {
        String query = "SELECT * FROM company";
        String columnName = "name"; // замените на существующее поле, если имя другое

        List<String> companyNames = DatabaseUtils.getSingleColumnValues(query, columnName);

        // ✅ Проверяем, что список не пустой
        assertFalse(companyNames.isEmpty(), "Список компаний не должен быть пустым");

        // 🖨 Выводим имена компаний для отладки (по желанию)
        companyNames.forEach(name -> System.out.println("Компания: " + name));
    }
}
