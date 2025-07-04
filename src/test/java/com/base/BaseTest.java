package com.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.utils.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.ui.tests.LoginTestBase.logger;

public class BaseTest {

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // 🔧 Отключаем встроенные уведомления Chrome
        options.addArguments(
                "--disable-popup-blocking",
                "--disable-notifications",
                "--disable-infobars",
                "--disable-save-password-bubble",
                "--disable-translate",
                "--no-default-browser-check",
                "--start-maximized",
                "--incognito"
        );

        // ⬇️ Связываем кастомные опции с Selenide
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null; // потому что уже стартуем в максимальном размере
        Configuration.baseUrl = "https://www.saucedemo.com";
    }

    @BeforeAll
    static void validateTestConfig() {
        String login = TestConfig.getLogin("standard_user");
        String password = TestConfig.getPassword("standard_user");
        if (login.isEmpty() || password.isEmpty()) {
            throw new IllegalStateException("Конфигурация логина/пароля для standard_user не задана или пуста");
        }
        logger.info("Конфиг прошёл проверку. Логин: {}, пароль: <скрыт>", login);
    }

    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}