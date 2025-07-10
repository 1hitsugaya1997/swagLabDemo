package com.api.reqres.tests.PATCH;

import com.api.reqres.clients.UserClient;
import com.api.reqres.dto.PatchUser.PatchUserRequest;
import com.api.reqres.dto.PatchUser.PatchUserRequest;
import com.api.reqres.dto.PatchUser.PatchUserResponse;
import com.api.reqres.utils.TimeAssertions;
import com.api.reqres.utils.UserDataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Reqres API")
@Feature("PATCH /api/users/id")
@Story("Изменение пользователя")
@Severity(SeverityLevel.CRITICAL)

public class PatchUserApiTest {
    private final UserClient userClient = new UserClient();
    String randomName = UserDataGenerator.generateName();
    String randomJob = UserDataGenerator.generateJob();
    private final String id = "2";

    @Test
    @DisplayName("Изменение данных  пользователя с валидными данными")
    @Description("Проверяет успешное изменение пользователя через PATCH /api/users/шв")
    void shouldPatchUserSuccessfully() {
        PatchUserRequest request = PatchUserRequest.builder()
                .name(randomName)
                .job(randomJob)
                .build();

        Response response = step("Выполнить PATCH-запрос для изменения пользователя",
                () -> userClient.patchUser(request, id));

        PatchUserResponse responseBody = step("Десериализовать ответ в CreateUserResponse",
                () -> response.as(PatchUserResponse.class));

        step("Проверить статус код и поля ответа", () -> {
            assertEquals(200, response.statusCode(), "Ожидается статус 200");
            assertEquals(request.getName(), responseBody.getName());
            assertEquals(request.getJob(), responseBody.getJob());
            assertNotNull(responseBody.getUpdatedAt(), "UpdatedAt не должен быть null");
            TimeAssertions.assertTimeCloseToNow(responseBody.getUpdatedAt(), 10); // допускаем 10 секунд дельты
        });
    }
}
