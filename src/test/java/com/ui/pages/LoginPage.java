package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // 🔍 XPath локаторы
    private static final String USERNAME_INPUT_XPATH = "//input[@placeholder='Username']";
    private static final String PASSWORD_INPUT_XPATH = "//input[@placeholder='Password']";
    private static final String LOGIN_BUTTON_XPATH = "//input[@data-test='login-button']";
    private static final String SWAG_LABS_TITLE_XPATH = "//div[text()='Swag Labs']";
    private static final String ERROR_MESSAGE_XPATH = "//h3[@data-test='error']";

    // 🔍 Элементы страницы
    private final SelenideElement usernameInput = $x(USERNAME_INPUT_XPATH);         // Поле для ввода логина
    private final SelenideElement passwordInput = $x(PASSWORD_INPUT_XPATH);         // Поле для ввода пароля
    private final SelenideElement loginButton = $x(LOGIN_BUTTON_XPATH);             // Кнопка входа (Login)
    private final SelenideElement swagLabsTitle = $x(SWAG_LABS_TITLE_XPATH);        // Заголовок страницы (успешный вход)
    private final SelenideElement errorMessage = $x(ERROR_MESSAGE_XPATH);           // Сообщение об ошибке при неудачном входе

    // ✅ Открыть страницу логина (базовый URL + "/")
    public LoginPage openLoginPage() {
        open("/");
        return this;
    }

    // ✅ Ввести логин
    public LoginPage enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
        return this;
    }

    // ✅ Ввести пароль
    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    // ✅ Клик по кнопке Login
    public void clickLogin() {
        loginButton.shouldBe(visible).click();
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
