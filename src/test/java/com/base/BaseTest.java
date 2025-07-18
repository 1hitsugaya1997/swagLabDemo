package com.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.utils.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static final int COUNT_BROWSER = TestConfig.getJunitFixedParallelism();
    private static final Semaphore BROWSER_SEMAPHORE = new Semaphore(COUNT_BROWSER);

    @BeforeAll
    static void globalSetup() {
        // ✅ Подключаем AllureSelenide Listener для автоматических скриншотов и исходного кода страницы
        addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );

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

        // 🛠️ Конфигурация Selenide
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = TestConfig.getBaseUrl();
        Configuration.reopenBrowserOnFail = false;
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 10000;

        // 📸 Указываем папку для хранения скриншотов и исходников (Selenide и Allure)
        Configuration.reportsFolder = "build/reports/tests";

        String login = TestConfig.getLogin("standard_user");
        String password = TestConfig.getPassword("standard_user");
        if (login.isEmpty() || password.isEmpty()) {
            throw new IllegalStateException("Конфигурация логина/пароля для standard_user не задана или пуста");
        }
        logger.info("Конфигурация прошла проверку. Логин: {}", login);
    }

    @BeforeEach
    void setup() {
        try {
            logger.info("Ожидаем разрешения для запуска браузера...");
            BROWSER_SEMAPHORE.acquire();
            logger.info("Разрешение получено, запускаем браузер. Поток: {}", Thread.currentThread().getName());

            WebDriverRunner.clearBrowserCache();
            com.codeborne.selenide.Selenide.open("/");
        } catch (InterruptedException e) {
            logger.error("Поток прерван во время ожидания разрешения запуска браузера", e);
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        try {
            WebDriverRunner.closeWebDriver();
        } catch (Exception e) {
            logger.error("Ошибка при закрытии браузера: ", e);
        } finally {
            BROWSER_SEMAPHORE.release();
            logger.info("Разрешение на запуск браузера освобождено. Поток: {}", Thread.currentThread().getName());
        }
    }
}
