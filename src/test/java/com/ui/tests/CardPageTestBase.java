package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.CardPage;
import com.ui.pages.CheckoutPage;
import com.ui.pages.HomePage;
import com.ui.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Страница корзины")
@Feature("Работа с корзиной")
public class CardPageTestBase extends BaseTest {

    static final Logger logger = LoggerFactory.getLogger(CardPageTestBase.class);

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка отображения страницы корзины")
    @DisplayName("Проверка заголовка страницы корзины")
    public void shouldBeOnCartPage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
        });

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
        });

        Allure.step("Проверить, что отображается заголовок 'Your Cart'", () -> {
            assertTrue(cardPage.isOnCartPage(), "Заголовок корзины не отображается");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Добавление и удаление товара из корзины")
    @DisplayName("Проверка добавления и удаления товара из корзины")
    public void addAndRemoveItemFromCartTest() {
        String itemName = "Sauce Labs Backpack";

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
        });

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
        });

        Allure.step("Перейти в корзину", homePage::clickCart);

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            List<String> items = cardPage.getCartItemNames();
            assertTrue(items.contains(itemName), "Товар не найден в корзине");
        });

        Allure.step("Удалить товар из корзины", () -> {
            cardPage.removeItemFromCart(itemName);
        });

        Allure.step("Проверить, что товар удалён из корзины", () -> {
            List<String> itemsAfter = cardPage.getCartItemNames();
            assertFalse(itemsAfter.contains(itemName), "Товар остался в корзине после удаления");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход к оформлению заказа")
    @DisplayName("Проверка перехода к форме оформления заказа")
    public void shouldProceedToCheckout() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);

        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Пользователь авторизован");
        });

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта корзина");
        });

        Allure.step("Нажать кнопку Checkout", () -> {
            cardPage.clickCheckout();
            logger.info("Нажата кнопка Checkout");
        });

        Allure.step("Проверить, что отображается форма оформления заказа (Checkout: Your Information)", () -> {
            assertTrue(checkoutPage.isOnCheckoutPage(), "Не отображается форма оформления заказа");
            logger.info("Форма оформления заказа отображается корректно");
        });
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Проверка пунктов меню на странице корзины")
    @DisplayName("Проверка отображения пунктов меню")
    public void shouldSeeMenuItemsOnCartPage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
        });

        Allure.step("Перейти в корзину", homePage::clickCart);

        Allure.step("Открыть меню и проверить наличие всех пунктов", () -> {
            List<String> expectedItems = List.of("All Items", "About", "Logout", "Reset App State");
            cardPage.clickOpenMenu();
            expectedItems.forEach(cardPage::shouldSeeMenuItem);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка перехода из меню в All Items")
    @DisplayName("Переход из корзины в All Items через меню")
    public void shouldReturnToAllItemsFromCartMenu() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        Allure.step("Авторизоваться", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
        });

        Allure.step("Перейти в корзину", homePage::clickCart);

        Allure.step("Вернуться в каталог товаров через меню", () -> {
            cardPage.clickOpenMenu();
            cardPage.clickMenuItem("All Items");
        });

        Allure.step("Проверить, что отображается список товаров", () -> {
            assertTrue(homePage.isOnInventoryPage(), "Не произошёл возврат на страницу товаров");
        });
    }
}
