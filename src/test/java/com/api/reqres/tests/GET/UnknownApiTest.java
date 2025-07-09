package com.api.reqres.tests.GET;

import com.api.reqres.clients.UserClient;
import com.api.reqres.dto.UnknownList.UnknownListResponse;
import com.api.reqres.dto.Unknown.UnknownResource;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        // ✅ Проверка статус-кода
        step("Проверить статус ответа", () ->
                assertEquals(200, response.statusCode(), "Ожидается статус 200"));

        // 📦 Десериализация в DTO
        UnknownListResponse listResponse = step("Десериализовать ответ в DTO UnknownListResponse",
                () -> response.as(UnknownListResponse.class));

        List<UnknownResource> resources = listResponse.getData();

        step("Проверить, что список ресурсов не пустой", () ->
                assertFalse(resources.isEmpty(), "Список ресурсов не должен быть пустым"));

        // 🔍 Проверка первого ресурса
        UnknownResource resource = resources.get(0);

        step("Проверить поля первого ресурса", () -> {
            assertNotNull(resource.getId(), "ID ресурса не должен быть null");
            assertTrue(resource.getId() > 0, "ID ресурса должен быть положительным");

            assertNotNull(resource.getName(), "Имя ресурса не должно быть null");
            assertNotNull(resource.getYear(), "Год ресурса не должен быть null");
            assertNotNull(resource.getColor(), "Цвет ресурса не должен быть null");
            assertNotNull(resource.getPantoneValue(), "Pantone значение ресурса не должно быть null");
        });
    }
}
