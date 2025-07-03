package com.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutPage {

    // 🔍 Элементы
    private final SelenideElement cartHeader = $(byXpath("//span[text()='Checkout: Your Information']"));
    private final SelenideElement firstNameInput = $(byXpath("//input[@placeholder='First Name']"));
    private final SelenideElement LastNameInput = $(byXpath("//input[@placeholder='Last Name']"));
    private final SelenideElement ZipPostalCodeInput = $(byXpath("//input[@placeholder='Zip/Postal Code']"));
    private final SelenideElement CancelButton = $(byXpath("//input[@data-test='cancel']"));
    private final SelenideElement ContinueButton = $(byXpath("//input[@data-test='cancel']"));
    private final SelenideElement menuButton = $(byXpath("//button[text()='Open Menu']"));

    // 🔍 Динамический локатор для кнопки "Remove"
    private SelenideElement getRemoveButton(String itemName) {
        return $(byXpath("//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button[text()='Remove']"));
    }

    // ✅ Проверка, что мы на странице корзины
    public boolean isOnCheckoutPage() {
        return cartHeader.shouldBe(visible).exists();
    }

    // ✅ Ввести имя
    public CheckoutPage enterFirstName(String FirstName) {
        firstNameInput.setValue(FirstName);
        return this;
    }

    // ✅ Ввести фамилию
    public CheckoutPage enterLastName(String LastName) {
        LastNameInput.setValue(LastName);
        return this;
    }
    // ✅ Ввести код
    public CheckoutPage enterCode(String Code) {
        ZipPostalCodeInput.setValue(Code);
        return this;
    }

    // ✅ Клик по кнопке Cancel
    public void clickCancel() {
        CancelButton.click();
    }

    // ✅ Клик по кнопке Cancel
    public void clickContinue() {
        ContinueButton.click();
    }

    // ✅ Клик по меню
    public void clickOpenMenu() {
        menuButton.shouldBe(visible).click();
    }

    // ✅ Убедиться, что пункт меню виден
    public void shouldSeeMenuItem(String itemName) {
        $x("//a[@class='bm-item menu-item' and text()='" + itemName + "']").shouldBe(visible);
    }

    // ✅ Клик по пункту меню
    public void clickMenuItem(String itemName) {
        $x("//a[@class='bm-item menu-item' and text()='" + itemName + "']").shouldBe(visible).click();
    }





    }
