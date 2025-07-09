package com.api.reqres.tests.POST;

import com.api.reqres.clients.UserClient;
import com.api.reqres.dto.CreateUser.CreateUserRequest;
import com.api.reqres.dto.CreateUser.CreateUserResponse;
import com.api.reqres.utils.UserDataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Reqres API")
@Feature("POST /api/users")
@Story("Создание пользователя")
@Severity(SeverityLevel.CRITICAL)
public class CreateUserApiTest {

    private final UserClient userClient = new UserClient();
    String randomName = UserDataGenerator.generateName();
    String randomJob = UserDataGenerator.generateJob();

    @Test
    @DisplayName("Создание нового пользователя с валидными данными")
    @Description("Проверяет успешное создание пользователя через POST /api/users")
    void shouldCreateUserSuccessfully() {
        CreateUserRequest request = CreateUserRequest.builder()
                .name(randomName)
                .job(randomJob)
                .build();

        Response response = step("Выполнить POST-запрос для создания пользователя",
                () -> userClient.createUser(request));

        CreateUserResponse responseBody = step("Десериализовать ответ в CreateUserResponse",
                () -> response.as(CreateUserResponse.class));

        step("Проверить статус код и поля ответа", () -> {
            assertEquals(201, response.statusCode(), "Ожидается статус 201 Created");
            assertEquals(request.getName(), responseBody.getName());
            assertEquals(request.getJob(), responseBody.getJob());
            assertNotNull(responseBody.getId(), "ID не должен быть null");
            assertNotNull(responseBody.getCreatedAt(), "createdAt не должен быть null");
        });
    }
}
