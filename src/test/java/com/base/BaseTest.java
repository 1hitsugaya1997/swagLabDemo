package com.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.utils.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    // Ограничиваем максимум 4 браузера одновременно (подкорректируй по своим ресурсам)
    private static final int COUNT_BROWSER = TestConfig.getJunitFixedParallelism();
    private static final Semaphore BROWSER_SEMAPHORE = new Semaphore(COUNT_BROWSER);

    @BeforeAll
    static void globalSetup() {
        // Настройка драйвера и конфигурации
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
        Configuration.browserSize = "1920x1080"; // Явно задаём размер (или null, если используешь --start-maximized)
        Configuration.baseUrl = TestConfig.getBaseUrl();
        Configuration.reopenBrowserOnFail = false;  // Не переоткрывать браузер при падении
        Configuration.holdBrowserOpen = false;      // Закрывать браузер после тестов
        Configuration.timeout = 10000; // Явный таймаут

        // Проверка конфигурации теста
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
            BROWSER_SEMAPHORE.acquire(); // Ждем разрешения на запуск браузера (лимит параллелизма)
            logger.info("Разрешение получено, запускаем браузер. Поток: {}", Thread.currentThread().getName());

            WebDriverRunner.clearBrowserCache(); // Очистка кеша (опционально)
            com.codeborne.selenide.Selenide.open("/");
        } catch (InterruptedException e) {
            logger.error("Поток прерван во время ожидания разрешения запуска браузера", e);
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            WebDriverRunner.closeWebDriver();
        } catch (Exception e) {
            logger.error("Ошибка при закрытии браузера: ", e);
        } finally {
            BROWSER_SEMAPHORE.release(); // Освобождаем разрешение, чтобы другой тест мог запустить браузер
            logger.info("Разрешение на запуск браузера освобождено. Поток: {}", Thread.currentThread().getName());
        }
    }
}
