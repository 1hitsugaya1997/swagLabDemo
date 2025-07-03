package com.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class CardPage {

    // 🔍 Элементы страницы корзины
    private final SelenideElement cartHeader = $(byXpath("//span[text()='Your Cart']"));
    private final ElementsCollection cartItems = $$(byXpath("//div[@class='cart_item']"));
    private final SelenideElement checkoutButton = $(byXpath("//button[@id='checkout']"));
    private final SelenideElement continueShoppingButton = $(byXpath("//button[@id='continue-shopping']"));
    private final SelenideElement menuButton = $(byXpath("//button[text()='Open Menu']"));

    // 🔍 Динамический локатор кнопки "Remove" для товара по имени
    private SelenideElement getRemoveButton(String itemName) {
        return $(byXpath("//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button[text()='Remove']"));
    }

    // ✅ Проверка: на странице корзины
    public boolean isOnCartPage() {
        return cartHeader.shouldBe(visible).exists();
    }

    // ✅ Получить список названий товаров в корзине
    public List<String> getCartItemNames() {
        return cartItems.stream()
                .map(item -> item.$(byXpath(".//div[@class='inventory_item_name']")).getText())
                .collect(Collectors.toList());
    }

    // ✅ Удалить товар из корзины по имени
    public void removeItemFromCart(String itemName) {
        getRemoveButton(itemName).shouldBe(visible).click();
    }

    // ✅ Нажать кнопку "Checkout" (оформление заказа)
    public void clickCheckout() {
        checkoutButton.shouldBe(visible).click();
    }

    // ✅ Нажать кнопку "Continue Shopping" (продолжить покупки)
    public void clickContinueShopping() {
        continueShoppingButton.shouldBe(visible).click();
    }

    // ✅ Открыть меню
    public void clickOpenMenu() {
        menuButton.shouldBe(visible).click();
    }

    // ✅ Проверить, что пункт меню виден
    public void shouldSeeMenuItem(String itemName) {
        $x("//a[@class='bm-item menu-item' and text()='" + itemName + "']").shouldBe(visible);
    }

    // ✅ Кликнуть по пункту меню
    public void clickMenuItem(String itemName) {
        $x("//a[@class='bm-item menu-item' and text()='" + itemName + "']").shouldBe(visible).click();
    }
}