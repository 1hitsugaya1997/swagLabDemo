package com.api.reqres.clients;

import com.api.reqres.dto.CreateUser.CreateUserRequest;
import com.api.reqres.dto.PatchUser.PatchUserRequest;
import com.api.reqres.dto.UpdateUser.UpdateUserRequest;
import com.utils.TestConfig;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private static final String BASE_URL = TestConfig.getReqresBaseUrl();

    public Response getListUsers(int page) {
        logger.info("Отправка GET запроса на {}/users?page={}", BASE_URL, page);

        Response response = given()
                .baseUri(BASE_URL)
                .header("x-api-ключ", "reqres-free-v1")
                .log().all()  // Лог запроса RestAssured
                .when()
                .get("users?page=" + page)
                .then()
                .log().all()  // Лог ответа RestAssured
                .extract()
                .response();

        logger.info("Получен ответ со статусом {}", response.statusCode());
        logger.debug("Тело ответа:\n{}", response.asPrettyString());

        return response;
    }

    public Response getSingleUsers(int id) {
        logger.info("Отправка GET запроса на {}/users/{}", BASE_URL, id);

        Response response = given()
                .baseUri(BASE_URL)
                .header("x-api-ключ", "reqres-free-v1")
                .log().all()  // Лог запроса RestAssured
                .when()
                .get("users/" + id)
                .then()
                .log().all()  // Лог ответа RestAssured
                .extract()
                .response()
                ;

        logger.info("Получен ответ со статусом {}", response.statusCode());
        logger.debug("Тело ответа:\n{}", response.asPrettyString());

        return response;
    }

    public Response getUnknown() {
        logger.info("Отправка GET запроса на {}/unknown", BASE_URL);

        Response response = given()
                .baseUri(BASE_URL)
                .header("x-api-key", "reqres-free-v1")
                .log().all()  // Лог запроса RestAssured
                .when()
                .get("unknown" )
                .then()
                .log().all()  // Лог ответа RestAssured
                .extract()
                .response()
                ;

        logger.info("Получен ответ со статусом {}", response.statusCode());
        logger.debug("Тело ответа:\n{}", response.asPrettyString());

        return response;
    }

    public Response createUser(CreateUserRequest request) {
        logger.info("POST /api/users с телом: {}", request);

        Response response = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(request)
                .log().all()
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .extract()
                .response();

        logger.info("Статус ответа: {}", response.statusCode());
        logger.debug("Ответ: {}", response.asPrettyString());

        return response;
    }

    public Response updateUser(UpdateUserRequest request, String id) {
        logger.info("POST /api/users с телом: {}", request);

        Response response = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(request)
                .log().all()
                .when()
                .put("/api/users/" + id)
                .then()
                .log().all()
                .extract()
                .response();

        logger.info("Статус ответа: {}", response.statusCode());
        logger.debug("Ответ: {}", response.asPrettyString());

        return response;
    }

    public Response patchUser(PatchUserRequest request, String id) {
        logger.info("Patch /api/users с телом: {}", request);

        Response response = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(request)
                .log().all()
                .when()
                .patch("/api/users/" + id)
                .then()
                .log().all()
                .extract()
                .response();

        logger.info("Статус ответа: {}", response.statusCode());
        logger.debug("Ответ: {}", response.asPrettyString());

        return response;
    }

}
