package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutPage {

    // 🔍 Заголовок страницы оформления
    private static final String CHECKOUT_HEADER_XPATH = "//span[text()='Checkout: Your Information']";
    private final SelenideElement checkoutHeader = $x(CHECKOUT_HEADER_XPATH);

    // 🔍 Поле ввода имени
    private static final String FIRST_NAME_INPUT_XPATH = "//input[@placeholder='First Name']";
    private final SelenideElement firstNameInput = $x(FIRST_NAME_INPUT_XPATH);

    // 🔍 Поле ввода фамилии
    private static final String LAST_NAME_INPUT_XPATH = "//input[@placeholder='Last Name']";
    private final SelenideElement lastNameInput = $x(LAST_NAME_INPUT_XPATH);

    // 🔍 Поле ввода почтового кода
    private static final String ZIP_CODE_INPUT_XPATH = "//input[@placeholder='Zip/Postal Code']";
    private final SelenideElement zipCodeInput = $x(ZIP_CODE_INPUT_XPATH);

    // 🔍 Кнопка Cancel
    private static final String CANCEL_BUTTON_XPATH = "//button[@data-test='cancel']";
    private final SelenideElement cancelButton = $x(CANCEL_BUTTON_XPATH);

    // 🔍 Кнопка Continue
    private static final String CONTINUE_BUTTON_XPATH = "//input[@data-test='continue']";
    private final SelenideElement continueButton = $x(CONTINUE_BUTTON_XPATH);

    // 🔍 Кнопка открытия меню
    private static final String MENU_BUTTON_XPATH = "//button[text()='Open Menu']";
    private final SelenideElement menuButton = $x(MENU_BUTTON_XPATH);

    // 🔍 Кнопка перехода в корзину
    private static final String CART_BUTTON_XPATH = "//a[@class='shopping_cart_link']";
    private final SelenideElement cartButton = $x(CART_BUTTON_XPATH);

    // ✅ Проверка, что мы на странице оформления заказа
    public boolean isOnCheckoutPage() {
        return checkoutHeader.shouldBe(visible).exists();
    }

    // ✅ Ввести имя
    public CheckoutPage enterFirstName(String firstName) {
        firstNameInput.shouldBe(visible).setValue(firstName);
        return this;
    }

    // ✅ Ввести фамилию
    public CheckoutPage enterLastName(String lastName) {
        lastNameInput.shouldBe(visible).setValue(lastName);
        return this;
    }

    // ✅ Ввести почтовый код
    public CheckoutPage enterCode(String postalCode) {
        zipCodeInput.shouldBe(visible).setValue(postalCode);
        return this;
    }

    // ✅ Клик по кнопке Cancel
    public void clickCancel() {
        cancelButton.shouldBe(visible).click();
    }

    // ✅ Клик по кнопке Continue
    public void clickContinue() {
        continueButton.shouldBe(visible).click();
    }

    // ✅ Клик по кнопке открытия меню
    public void clickOpenMenu() {
        menuButton.shouldBe(visible).click();
    }

    // ✅ Проверка видимости пункта меню
    public void shouldSeeMenuItem(String itemName) {
        $x(String.format("//a[@class='bm-item menu-item' and text()='%s']", itemName))
                .shouldBe(visible);
    }

    // ✅ Клик по пункту меню
    public void clickMenuItem(String itemName) {
        $x(String.format("//a[@class='bm-item menu-item' and text()='%s']", itemName))
                .shouldBe(visible)
                .click();
    }

    // ✅ Клик по иконке корзины
    public void clickCart() {
        cartButton.shouldBe(visible).click();
    }
}
