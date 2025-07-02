package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // 🔍 Элементы страницы
    private final SelenideElement usernameInput = $(byXpath("//input[@placeholder='Username']"));           // Поле для ввода логина
    private final SelenideElement passwordInput = $(byXpath("//input[@placeholder='Password']"));           // Поле для ввода пароля
    private final SelenideElement loginButton   = $(byXpath("//input[@data-test='login-button']"));         // Кнопка входа (Login)
    private final SelenideElement swagLabsTitle = $(byXpath("//div[text()='Swag Labs']"));                   // Заголовок страницы (для проверки успешного входа)
    private final SelenideElement errorMessage  = $(byXpath("//h3[@data-test='error']"));                    // Сообщение об ошибке при неудачном входе

    // ✅ Открыть страницу логина (базовый URL + "/")
    public LoginPage openLoginPage() {
        open("/");
        return this;
    }

    // ✅ Ввести логин
    public LoginPage enterUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    // ✅ Ввести пароль
    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    // ✅ Клик по кнопке Login
    public void clickLogin() {
        loginButton.click();
    }

    // ✅ Проверить, что на странице заголовок "Swag Labs" виден (успешный вход)
    public boolean isOnInventoryPage() {
        return swagLabsTitle.shouldBe(visible).exists();
    }

    // ✅ Получить текст сообщения об ошибке
    public String getErrorMessage() {
        return errorMessage.shouldBe(visible).getText();
    }
}
