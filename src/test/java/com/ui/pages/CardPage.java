package com.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class CardPage {

    // 🔍 Элементы
    private final SelenideElement cartHeader = $(byXpath("//span[text()='Your Cart']"));
    private final ElementsCollection cartItems = $$(byXpath("//div[@class='cart_item']"));
    private final SelenideElement checkoutButton = $(byXpath("//button[@id='checkout']"));

    // 🔍 Динамический локатор для кнопки "Remove"
    private SelenideElement getRemoveButton(String itemName) {
        return $(byXpath("//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button[text()='Remove']"));
    }

    // ✅ Проверка, что мы на странице корзины
    public boolean isOnCartPage() {
        return cartHeader.shouldBe(visible).exists();
    }

    // ✅ Получить список названий товаров в корзине
    public List<String> getCartItemNames() {
        return cartItems.stream()
                .map(item -> item.$(byXpath(".//div[@class='inventory_item_name']")).getText())
                .collect(Collectors.toList());
    }

    // ✅ Удалить товар по имени
    public void removeItemFromCart(String itemName) {
        getRemoveButton(itemName).shouldBe(visible).click();
    }

    // ✅ Перейти к оформлению
    public void clickCheckout() {
        checkoutButton.shouldBe(visible).click();
    }
}
