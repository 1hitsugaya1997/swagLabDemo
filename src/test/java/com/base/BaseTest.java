package com.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.utils.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.ui.tests.LoginTest.logger;

public class BaseTest {

    @BeforeAll
    public static void globalSetup() {
        // Один раз на весь тестовый запуск
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

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

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null; // стартуем в максимальном размере
        Configuration.baseUrl = "https://www.saucedemo.com";

        // Опционально, чтобы не создавать драйвер каждый раз заново, можно включить keepBrowserOpen:
        // Configuration.holdBrowserOpen = false; // false по умолчанию
    }

    @BeforeEach
    public void setup() {
        // Открываем базовый URL перед каждым тестом
        com.codeborne.selenide.Selenide.open("/");
    }

    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
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
}
