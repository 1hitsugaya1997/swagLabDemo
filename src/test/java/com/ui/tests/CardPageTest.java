package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.*;
import com.utils.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Страница корзины")
@Feature("Работа с корзиной")
public class CardPageTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CardPageTest.class);

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    private static final String STANDARD_USER = "standard_user";

    // Метод для авторизации стандартного пользователя
    private void loginStandardUser() {
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername(TestConfig.getLogin(STANDARD_USER));
            loginPage.enterPassword(TestConfig.getPassword(STANDARD_USER));
            loginPage.clickLogin();
            logger.info("Пользователь '{}' успешно авторизован", STANDARD_USER);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка отображения страницы корзины")
    @DisplayName("Проверка заголовка страницы корзины")
    public void shouldBeOnCartPage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        loginStandardUser();

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта страница корзины");
        });

        Allure.step("Проверить, что отображается заголовок 'Your Cart'", () -> {
            assertTrue(cardPage.isOnCartPage(), "Заголовок корзины 'Your Cart' не отображается");
            logger.info("Заголовок корзины отображается корректно");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Добавление и удаление товара из корзины")
    @DisplayName("Проверка добавления и удаления товара из корзины")
    public void addAndRemoveItemFromCartTest() {
        String itemName = "Sauce Labs Backpack";

        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        loginStandardUser();

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта корзина");
        });

        Allure.step("Проверить, что товар отображается в корзине", () -> {
            List<String> items = cardPage.getCartItemNames();
            assertTrue(items.contains(itemName), "Товар '" + itemName + "' не найден в корзине");
            logger.info("Товар '{}' присутствует в корзине", itemName);
        });

        Allure.step("Удалить товар из корзины: " + itemName, () -> {
            cardPage.removeItemFromCart(itemName);
            logger.info("Удалён товар: {}", itemName);
        });

        Allure.step("Проверить, что товар удалён из корзины", () -> {
            List<String> itemsAfter = cardPage.getCartItemNames();
            assertFalse(itemsAfter.contains(itemName), "Товар '" + itemName + "' остался в корзине после удаления");
            logger.info("Товар '{}' отсутствует в корзине после удаления", itemName);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход к оформлению заказа")
    @DisplayName("Проверка перехода к форме оформления заказа")
    public void shouldProceedToCheckout() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        loginStandardUser();

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта корзина");
        });

        Allure.step("Нажать кнопку Checkout", () -> {
            cardPage.clickCheckout();
            logger.info("Нажата кнопка Checkout");
        });

        Allure.step("Проверить, что отображается форма оформления заказа (Checkout: Your Information)", () -> {
            assertTrue(checkoutPage.isOnCheckoutPage(), "Форма оформления заказа не отображается");
            logger.info("Форма оформления заказа отображается корректно");
        });
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Проверка пунктов меню на странице корзины")
    @DisplayName("Проверка отображения пунктов меню")
    public void shouldSeeMenuItemsOnCartPage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        loginStandardUser();

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта корзина");
        });

        Allure.step("Открыть меню и проверить наличие всех пунктов", () -> {
            List<String> expectedItems = List.of("All Items", "About", "Logout", "Reset App State");
            cardPage.clickOpenMenu();
            expectedItems.forEach(item -> {
                cardPage.shouldSeeMenuItem(item);
                logger.info("Пункт меню '{}' отображается", item);
            });
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Проверка перехода из меню в All Items")
    @DisplayName("Переход из корзины в All Items через меню")
    public void shouldReturnToAllItemsFromCartMenu() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        loginStandardUser();

        Allure.step("Перейти в корзину", () -> {
            homePage.clickCart();
            logger.info("Открыта корзина");
        });

        Allure.step("Вернуться в каталог товаров через меню", () -> {
            cardPage.clickOpenMenu();
            cardPage.clickMenuItem("All Items");
            logger.info("Через меню выполнен переход в 'All Items'");
        });

        Allure.step("Проверить, что отображается список товаров", () -> {
            assertTrue(homePage.isOnInventoryPage(), "Не произошёл возврат на страницу товаров");
            logger.info("Возврат на страницу товаров выполнен успешно");
        });
    }
}
