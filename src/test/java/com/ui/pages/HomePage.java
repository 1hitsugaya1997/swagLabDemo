package com.ui.pages;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    // 🔍 XPath-локаторы
    private static final String TITLE_XPATH = "//div[text()='Swag Labs']";
    private static final String PRODUCT_HEADER_XPATH = "//span[text()='Products']";
    private static final String SORT_DROPDOWN_XPATH = "//select[@class='product_sort_container']";
    private static final String MENU_BUTTON_XPATH = "//button[text()='Open Menu']";
    private static final String ERROR_MESSAGE_XPATH = "//h3[@data-test='error']";
    private static final String CART_BUTTON_XPATH = "//a[@class='shopping_cart_link']";
    private static final String FIRST_PRODUCT_NAME_XPATH = "//div[@class='inventory_item'][1]//div[@class='inventory_item_name ']";
    private static final String ITEM_NAME_XPATH = "//div[@class='inventory_item_name' and text()='%s']";
    private static final String ITEM_PRICE_XPATH = "//div[@class='inventory_item_price']";
    private static final String MENU_ITEM_XPATH = "//a[@class='bm-item menu-item' and text()='%s']";
    private static final String LOGIN_USERNAME_XPATH = "//input[@data-test='username']";
    private static final String LOGIN_PASSWORD_XPATH = "//input[@data-test='password']";
    private static final String LOGIN_SUBMIT_XPATH = "//input[@type='submit']";
    private static final String ITEM_CART_BUTTON_XPATH = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";

    // 🔍 Элементы
    private final SelenideElement title = $x(TITLE_XPATH);
    private final SelenideElement productHeader = $x(PRODUCT_HEADER_XPATH);
    private final SelenideElement sortDropdown = $x(SORT_DROPDOWN_XPATH);
    private final SelenideElement menuButton = $x(MENU_BUTTON_XPATH);
    private final SelenideElement errorMessage = $x(ERROR_MESSAGE_XPATH);
    private final SelenideElement cartButton = $x(CART_BUTTON_XPATH);
    private final SelenideElement firstProductName = $x(FIRST_PRODUCT_NAME_XPATH);

    // 🔍 Динамический локатор для кнопки товара
    private SelenideElement getCartButton(String itemName) {
        return $x(String.format(ITEM_CART_BUTTON_XPATH, itemName));
    }

    // ✅ Проверка, что находимся на странице товаров
    public boolean isOnInventoryPage() {
        return productHeader.shouldBe(visible).exists();
    }

    // ✅ Проверка логотипа
    public boolean isLogoVisible() {
        return title.shouldBe(visible).exists();
    }

    // ✅ Получить имя первого товара
    public String getFirstProductName() {
        return firstProductName.shouldBe(visible).getText();
    }

    // ✅ Клик по корзине
    public void clickCart() {
        cartButton.shouldBe(visible).click();
    }

    // ✅ Выбрать вариант сортировки
    public void selectSortOption(String visibleText) {
        sortDropdown.shouldBe(visible).selectOption(visibleText);
    }

    // ✅ Добавить товар в корзину
    public void addItemToCart(String itemName) {
        getCartButton(itemName).shouldBe(visible).click();
    }

    // ✅ Удалить товар из корзины
    public void removeItemFromCart(String itemName) {
        getCartButton(itemName).shouldBe(visible).click();
    }

    // ✅ Проверить отображение товара в корзине
    public void shouldSeeItemInCart(String itemName) {
        $x(String.format(ITEM_NAME_XPATH, itemName)).shouldBe(visible);
    }

    // ✅ Проверить отсутствие товара в корзине
    public void shouldNotSeeItemInCart(String itemName) {
        $x(String.format(ITEM_NAME_XPATH, itemName)).shouldNotBe(visible);
    }

    // ✅ Клик по кнопке меню
    public void clickOpenMenu() {
        menuButton.shouldBe(visible).click();
    }

    // ✅ Проверка видимости пункта меню
    public void shouldSeeMenuItem(String itemName) {
        $x(String.format(MENU_ITEM_XPATH, itemName)).shouldBe(visible);
    }

    // ✅ Клик по пункту меню
    public void clickMenuItem(String itemName) {
        $x(String.format(MENU_ITEM_XPATH, itemName)).shouldBe(visible).click();
    }

    // ✅ Получить текст ошибки (например, после неудачного логина)
    public String getErrorMessage() {
        return errorMessage.shouldBe(visible).getText();
    }

    // ✅ Получить список всех цен товаров
    public List<Double> getAllProductPrices() {
        return $$x(ITEM_PRICE_XPATH)
                .stream()
                .map(el -> el.getText().replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    // ✅ Получить список всех названий товаров
    public List<String> getAllProductNames() {
        return $$x("//div[@class='inventory_item_name']")
                .stream()
                .map(SelenideElement::getText)
                .toList();
    }

    // ✅ Выполнить логин со страницы (если форма доступна)
    public void loginAs(String username, String password) {
        $x(LOGIN_USERNAME_XPATH).shouldBe(visible).setValue(username);
        $x(LOGIN_PASSWORD_XPATH).shouldBe(visible).setValue(password);
        $x(LOGIN_SUBMIT_XPATH).shouldBe(visible).click();
    }
}
