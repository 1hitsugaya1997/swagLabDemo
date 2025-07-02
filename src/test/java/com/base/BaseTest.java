package com.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}