package com.api.reqres.tests.GET;

import com.api.reqres.clients.UserClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Reqres API")
@Feature("GET /unknown")
@Story("Получение списка ресурсов")
@Severity(SeverityLevel.CRITICAL)
public class UnknownApiTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Позитивный тест: получение списка ресурсов /unknown")
    @Description("Проверяет успешный ответ и корректность данных при запросе ресурсов через /unknown")
    void shouldGetUnknownResources() {

        // 🔍 Выполняем запрос
        Response response = step("Выполнить GET-запрос /unknown",
                () -> userClient.getUnknown());

        // ✅ Проверяем статус
        step("Проверить статус ответа", () ->
                assertEquals(200, response.statusCode(), "Ожидается статус 200"));

        // 📦 Получаем список ресурсов
        List<Map<String, Object>> dataList = step("Извлечь список объектов 'data' из тела ответа",
                () -> response.jsonPath().getList("data"));

        step("Проверить, что список ресурсов не пустой", () ->
                assertFalse(dataList.isEmpty(), "Список ресурсов не должен быть пустым"));

        // 🔍 Проверка первого ресурса
        Map<String, Object> resource = dataList.get(0);

        step("Проверить поля первого ресурса", () -> {
            assertNotNull(resource.get("id"), "ID ресурса не должен быть null");
            assertTrue((Integer) resource.get("id") > 0, "ID ресурса должен быть положительным");

            assertNotNull(resource.get("name"), "Имя ресурса не должно быть null");
            assertNotNull(resource.get("year"), "Год ресурса не должен быть null");
            assertNotNull(resource.get("color"), "Цвет ресурса не должен быть null");
            assertNotNull(resource.get("pantone_value"), "Pantone значение ресурса не должно быть null");
        });
    }
}
