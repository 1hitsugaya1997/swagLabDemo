package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.LoginPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Авторизация")
@Feature("Форма логина")
public class LoginTestBase extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTestBase.class);

    private final LoginPage loginPage = new LoginPage();

    @ParameterizedTest(name = "{index} => login=''{0}'', password=''{1}'', expectedError=''{2}''")
    @CsvSource({
            "standard_user, secret_sauce, ''",
            "wrong_user, wrong_password, Username and password do not match",
            "'', '', Username is required",
            "'', secret_sauce, Username is required",
            "standard_user, '', Password is required",
            "locked_out_user, secret_sauce, ''",
            "problem_user, secret_sauce, ''",
            "performance_glitch_user, secret_sauce, ''",
            "error_user, secret_sauce, ''"
    })
    @Severity(SeverityLevel.CRITICAL)
    @Story("User login scenarios")
    @org.junit.jupiter.api.DisplayName("Параметризованный тест входа с разными логинами и паролями")
    public void loginTest(String username, String password, String expectedError) {
        logger.info("Тест запускается с параметрами: username='{}', password='{}', expectedError='{}'",
                username.isEmpty() ? "<пустой>" : username,
                password.isEmpty() ? "<пустой>" : "<скрыт>",
                expectedError.isEmpty() ? "<пустой>" : expectedError);

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        logger.info("Открыта страница логина");

        Allure.step("Ввести логин: " + (username.isEmpty() ? "<пустой>" : username), () -> {
            loginPage.enterUsername(username);
            logger.info("Введён логин: {}", username.isEmpty() ? "<пустой>" : username);
        });

        Allure.step("Ввести пароль: " + (password.isEmpty() ? "<пустой>" : "<скрыт>"), () -> {
            loginPage.enterPassword(password);
            logger.info("Введён пароль: {}", password.isEmpty() ? "<пустой>" : "<скрыт>");
        });

        Allure.step("Нажать кнопку Login", () -> {
            loginPage.clickLogin();
            logger.info("Нажата кнопка Login");
        });

        if (expectedError.isEmpty()) {
            Allure.step("Проверить, что пользователь на странице инвентаря", () -> {
                assertTrue(loginPage.isOnInventoryPage(), "Страница инвентаря не отображается");
                logger.info("Проверка успешного входа пройдена");
            });
        } else {
            Allure.step("Проверить сообщение об ошибке", () -> {
                String actualError = loginPage.getErrorMessage();
                logger.info("Получено сообщение об ошибке: {}", actualError);
                assertTrue(actualError.contains(expectedError),
                        "Ошибка не совпадает. Ожидали: \"" + expectedError + "\", получили: \"" + actualError + "\"");
            });
        }
    }
}
