package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.*;
import com.utils.TestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Оформление заказа")
@Feature("Завершение оформления")
public class CheckoutCompletePageTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutCompletePageTest.class);

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
    private final CheckoutCompletePage completePage = new CheckoutCompletePage();

    private static final String STANDARD_USER = "standard_user";

    private void loginStandardUser() {
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername(TestConfig.getLogin(STANDARD_USER));
            loginPage.enterPassword(TestConfig.getPassword(STANDARD_USER));
            loginPage.clickLogin();
            logger.info("Пользователь '{}' успешно авторизован", STANDARD_USER);
        });
    }

    private void prepareOrderWithItem(String itemName) {
        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Товар '{}' добавлен в корзину", itemName);
        });

        Allure.step("Перейти в корзину и оформить заказ", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
            logger.info("Данные для оформления заказа введены");
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Успешное завершение заказа")
    @DisplayName("Проверка отображения страницы завершения заказа")
    public void shouldDisplayCompletePage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Нажать кнопку Finish", () -> {
            overviewPage.clickFinish();
            logger.info("Нажата кнопка Finish");
        });

        Allure.step("Проверить текст благодарности", () -> {
            String message = completePage.getCompleteMessage();
            assertTrue(message.contains("Your order has been dispatched") || message.contains("dispatched"),
                    "Текст благодарности не соответствует ожидаемому");
            logger.info("Текст благодарности подтверждён: {}", message);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Возврат на главную страницу")
    @DisplayName("Проверка перехода на главную по кнопке 'Back Home'")
    public void shouldReturnToHomePage() {
        Allure.step("Открыть страницу логина", loginPage::openLoginPage);
        logger.info("Страница логина открыта");

        loginStandardUser();

        String itemName = "Sauce Labs Backpack";
        prepareOrderWithItem(itemName);

        Allure.step("Нажать кнопку Finish", () -> {
            overviewPage.clickFinish();
            logger.info("Нажата кнопка Finish");
        });

        Allure.step("Нажать кнопку Back Home", () -> {
            completePage.clickBackHome();
            logger.info("Нажата кнопка Back Home");
        });

        Allure.step("Проверить переход на главную страницу", () -> {
            assertTrue(homePage.isOnInventoryPage(),
                    "Переход на главную страницу после завершения заказа не выполнен");
            logger.info("Успешный переход на главную страницу");
        });
    }
}
