package com.api.reqres.tests.GET;

import com.api.reqres.clients.UserClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Reqres API")
@Feature("GET /users/{id}")
@Story("Получение пользователя по ID")
@Severity(SeverityLevel.CRITICAL)
public class SingleUserApiTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Позитивный тест: получить пользователя с валидным ID")
    @Description("Проверяет успешный ответ и корректность данных при запросе пользователя по ID")
    void shouldGetUsersById() {
        final int id = 2;

        // 🔍 Выполняем запрос
        Response response = step("Выполнить GET-запрос на получение пользователя с ID = " + id,
                () -> userClient.getSingleUsers(id));

        // ✅ Проверяем статус
        step("Проверить статус ответа", () ->
                assertEquals(200, response.statusCode(), "Ожидается статус 200"));

        // 📦 Получаем данные пользователя
        Map<String, Object> userData = step("Извлечь объект 'data' из тела ответа",
                () -> response.jsonPath().getMap("data"));

        step("Проверить, что данные пользователя не null", () ->
                assertNotNull(userData, "Данные пользователя не должны быть null"));

        // 🔍 Проверяем поля
        Integer userId = (Integer) userData.get("id");
        String email = (String) userData.get("email");
        String firstName = (String) userData.get("first_name");
        String lastName = (String) userData.get("last_name");

        step("Проверить поля пользователя", () -> {
            assertNotNull(userId, "ID пользователя не должен быть null");
            assertTrue(userId > 0, "ID пользователя должен быть положительным");

            assertNotNull(email, "Email пользователя не должен быть null");
            assertTrue(email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"), "Email должен быть валидным");

            assertNotNull(firstName, "Имя пользователя не должно быть null");
            assertNotNull(lastName, "Фамилия пользователя не должна быть null");
        });
    }


}
