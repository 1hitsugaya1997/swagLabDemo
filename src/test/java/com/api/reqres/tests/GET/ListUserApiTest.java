package com.api.reqres.tests.GET;

import com.api.reqres.clients.UserClient;
import com.api.reqres.dto.ListUsersResponse;
import com.api.reqres.dto.User;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        // 🔍 Выполняем GET-запрос
        Response response = step("Выполнить GET-запрос списка пользователей на странице " + page,
                () -> userClient.getListUsers(page));

        // ✅ Десериализация в DTO
        ListUsersResponse listUsers = step("Десериализовать ответ в DTO ListUsersResponse",
                () -> response.as(ListUsersResponse.class));

        // ✅ Проверка статус-кода
        step("Проверить статус код и номер страницы", () -> {
            assertEquals(200, response.statusCode(), "Ожидается статус 200");
            assertEquals(page, listUsers.getPage(), "Номер страницы должен совпадать");
        });

        // 📦 Работаем с DTO: список пользователей
        List<User> users = listUsers.getData();

        step("Проверить, что список пользователей не пустой", () ->
                assertFalse(users.isEmpty(), "Список пользователей не должен быть пустым"));

        // 🔍 Проверка первого пользователя
        User firstUser = users.get(0);

        step("Проверить поля первого пользователя", () -> {
            assertNotNull(firstUser.getId(), "ID пользователя не должен быть null");
            assertTrue(firstUser.getId() > 0, "ID пользователя должен быть положительным");

            assertNotNull(firstUser.getEmail(), "Email не должен быть null");
            assertTrue(firstUser.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"),
                    "Email должен быть валидным");

            assertNotNull(firstUser.getFirstName(), "Имя не должно быть null");
            assertNotNull(firstUser.getLastName(), "Фамилия не должна быть null");
        });
    }
}
