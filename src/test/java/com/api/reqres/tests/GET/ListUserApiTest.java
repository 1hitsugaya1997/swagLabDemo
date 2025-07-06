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
@Feature("GET /users")
@Story("Получение списка пользователей")
@Severity(SeverityLevel.CRITICAL)
public class ListUserApiTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Позитивный тест: получить список пользователей с валидным номером страницы")
    @Description("Проверяет успешный ответ и корректность данных при запросе списка пользователей по странице")
    void shouldGetListUsersByPage() {
        final int page = 2;

        // 🔍 Выполняем запрос
        Response response = step("Выполнить GET-запрос списка пользователей на странице " + page,
                () -> userClient.getListUsers(page));

        // ✅ Проверяем базовые поля
        step("Проверить статус код и номер страницы", () -> {
            assertEquals(200, response.statusCode(), "Ожидается статус 200");
            assertEquals(page, response.jsonPath().getInt("page"), "Номер страницы должен совпадать");
        });

        // 🧾 Получаем список пользователей
        List<Map<String, Object>> users = step("Получить список пользователей из тела ответа",
                () -> response.jsonPath().getList("data"));

        step("Проверить, что список пользователей не пустой", () ->
                assertFalse(users.isEmpty(), "Список пользователей не должен быть пустым"));

        // 🔍 Проверяем первого пользователя
        Map<String, Object> firstUser = users.get(0);
        Integer id = (Integer) firstUser.get("id");
        String email = (String) firstUser.get("email");
        String firstName = (String) firstUser.get("first_name");
        String lastName = (String) firstUser.get("last_name");

        step("Проверить поля первого пользователя", () -> {
            assertNotNull(id, "ID пользователя не должен быть null");
            assertTrue(id > 0, "ID пользователя должен быть положительным");

            assertNotNull(email, "Email пользователя не должен быть null");
            assertTrue(email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"), "Email должен быть валидным");

            assertNotNull(firstName, "Имя пользователя не должно быть null");
            assertNotNull(lastName, "Фамилия пользователя не должна быть null");
        });
    }


}
