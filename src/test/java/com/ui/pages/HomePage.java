package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    // 🔍 Статические элементы страницы
    private final SelenideElement title         = $(byXpath("//div[text()='Swag Labs']"));
    private final SelenideElement productHeader = $(byXpath("//span[text()='Products']"));
    private final SelenideElement sortDropdown  = $(byXpath("//select[@class='product_sort_container']"));
    private final SelenideElement menuButton    = $(byXpath("//button[text()='Open Menu']"));
    private final SelenideElement errorMessage  = $(byXpath("//h3[@data-test='error']"));
    private final SelenideElement cartButton    = $(byXpath("//a[@class='shopping_cart_link']"));
    private final SelenideElement firstProductName = $(byXpath("//div[@class='inventory_item'][1]//div[@class='inventory_item_name ']"));

    // 🔄 Универсальный локатор кнопки "Add to cart" / "Remove"
    private SelenideElement getCartButton(String itemName) {
        return $(byXpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button"));
    }

    // ✅ Получить имя первого товара
    public String getFirstProductName() {
        return firstProductName.shouldBe(visible).getText();
    }

    // ✅ Клик по корзине
    public void clickCart() {
        cartButton.shouldBe(visible).click();
    }

    // ✅ Выбрать сортировку
    public void selectSortOption(String visibleText) {
        sortDropdown.shouldBe(visible).selectOption(visibleText);
    }

    // ✅ Добавить товар в корзину
    public void addItemToCart(String itemName) {
        getCartButton(itemName).shouldBe(visible).click(); // label: Add to cart
    }

    // ✅ Удалить товар из корзины
    public void removeItemFromCart(String itemName) {
        getCartButton(itemName).shouldBe(visible).click(); // label: Remove
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

    // ✅ Проверка, что товар в корзине отображается
    public void shouldSeeItemInCart(String itemName) {
        $x("//div[@class='inventory_item_name' and text()='" + itemName + "']").shouldBe(visible);
    }

    // ✅ Проверка, что товара нет в корзине
    public void shouldNotSeeItemInCart(String itemName) {
        $x("//div[@class='inventory_item_name' and text()='" + itemName + "']").shouldNotBe(visible);
    }

    // ✅ Проверка на странице инвентаря
    public boolean isOnInventoryPage() {
        return productHeader.shouldBe(visible).exists();
    }

    // ✅ Проверка отображения логотипа
    public boolean isLogoVisible() {
        return title.shouldBe(visible).exists();
    }

    // ✅ Получить текст ошибки
    public String getErrorMessage() {
        return errorMessage.shouldBe(visible).getText();
    }

    // ✅ Получить все цены товаров (для проверки сортировки)
    public List<Double> getAllProductPrices() {
        return $$x("//div[@class='inventory_item_price']")
                .stream()
                .map(el -> el.getText().replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    // ✅ Получить все имена товаров (для проверки сортировки)
    public List<String> getAllProductNames() {
        return $$x("//div[@class='inventory_item_name']")
                .stream()
                .map(SelenideElement::getText)
                .toList();
    }

    // ✅ Логин прямо со страницы (если логика там)
    public void loginAs(String username, String password) {
        $x("//input[@data-test='username']").shouldBe(visible).setValue(username);
        $x("//input[@data-test='password']").shouldBe(visible).setValue(password);
        $x("//input[@type='submit']").shouldBe(visible).click();
    }
}
