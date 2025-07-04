package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.LoginPage;
import com.utils.TestConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.Arguments;

@Epic("Авторизация")
@Feature("Форма логина")
public class LoginTestBase extends BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(LoginTestBase.class);

    private final LoginPage loginPage = new LoginPage();

    private String maskPassword(String pass) {
        return pass == null || pass.isEmpty() ? "<пустой>" : "<скрыт>";
    }

    // Метод-источник данных для параметризованного теста
    static Stream<Arguments> loginData() {
        return Stream.of(
                arguments(TestConfig.getLogin("standard_user"), TestConfig.getPassword("standard_user"), ""),
                arguments("wrong_user", "wrong_password", "Username and password do not match"),
                arguments("", "", "Username is required"),
                arguments("", "secret_sauce", "Username is required"),
                arguments("standard_user", "", "Password is required"),
                arguments(TestConfig.getLogin("locked_out_user"), TestConfig.getPassword("locked_out_user"), ""),
                arguments(TestConfig.getLogin("problem_user"), TestConfig.getPassword("problem_user"), ""),
                arguments(TestConfig.getLogin("performance_glitch_user"), TestConfig.getPassword("performance_glitch_user"), ""),
                arguments(TestConfig.getLogin("error_user"), TestConfig.getPassword("error_user"), "")
        );
    }

    @ParameterizedTest(name = "{index} => Логин: '{0}', Пароль: '{1}', Ожидаемая ошибка: '{2}'")
    @MethodSource("loginData")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User login scenarios")
    @DisplayName("Параметризованный тест входа с разными логинами и паролями")
    public void loginTest(String username, String password, String expectedError) {
        logger.info("Запуск теста с параметрами: username='{}', password='{}', expectedError='{}'",
                username.isEmpty() ? "<пустой>" : username,
                maskPassword(password),
                expectedError.isEmpty() ? "<пустой>" : expectedError);

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        Allure.step("Ввести логин", () -> {
            loginPage.enterUsername(username);
            logger.info("Введён логин: {}", username.isEmpty() ? "<пустой>" : username);
        });

        Allure.step("Ввести пароль", () -> {
            loginPage.enterPassword(password);
            logger.info("Введён пароль: {}", maskPassword(password));
        });

        Allure.step("Нажать кнопку Login", () -> {
            loginPage.clickLogin();
            logger.info("Нажата кнопка Login");
        });

        if (expectedError.isEmpty()) {
            Allure.step("Проверить успешный вход", () -> {
                Assertions.assertTrue(loginPage.isOnInventoryPage(),
                        "Страница Inventory не отображается после входа");
                logger.info("Успешный вход подтвержден");
            });
        } else {
            Allure.step("Проверить сообщение об ошибке", () -> {
                String actualError = loginPage.getErrorMessage();
                logger.info("Фактическое сообщение об ошибке: '{}'", actualError);
                Assertions.assertTrue(
                        actualError.contains(expectedError),
                        () -> String.format("Ожидали ошибку: '%s', но получили: '%s'", expectedError, actualError)
                );
            });
        }
    }
}
