package com.api.reqres.tests.GET;

import com.api.reqres.clients.UserClient;
import com.api.reqres.dto.SingleUserResponse;
import com.api.reqres.dto.User;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        // ✅ Проверка статус-кода
        step("Проверить статус ответа", () ->
                assertEquals(200, response.statusCode(), "Ожидается статус 200"));

        // 📦 Десериализуем в DTO
        SingleUserResponse userResponse = step("Десериализовать ответ в SingleUserResponse",
                () -> response.as(SingleUserResponse.class));

        User user = userResponse.getData();

        step("Проверить, что объект user не null", () ->
                assertNotNull(user, "Пользователь не должен быть null"));

        // 🔍 Проверка полей пользователя
        step("Проверить поля пользователя", () -> {
            assertNotNull(user.getId(), "ID пользователя не должен быть null");
            assertTrue(user.getId() > 0, "ID пользователя должен быть положительным");

            assertNotNull(user.getEmail(), "Email пользователя не должен быть null");
            assertTrue(user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"),
                    "Email должен быть валидным");

            assertNotNull(user.getFirstName(), "Имя пользователя не должно быть null");
            assertNotNull(user.getLastName(), "Фамилия пользователя не должна быть null");
        });
    }
}
