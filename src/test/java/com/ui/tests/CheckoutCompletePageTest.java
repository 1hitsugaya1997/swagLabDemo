package com.ui.tests;

import com.base.BaseTest;
import com.ui.pages.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ui.tests.CardPageTestBase.logger;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Оформление заказа")
@Feature("Завершение оформления")

public class CheckoutCompletePageTest extends BaseTest {

    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    CheckoutPage checkoutPage = new CheckoutPage();
    CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage();
    CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();
    private final CardPage cardPage = new CardPage();
    private final CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Успешное завершение заказа")
    @DisplayName("Проверка, что отображается страница завершения заказа")
    public void shouldDisplayCompletePage() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Нажать кнопку Finish", overviewPage::clickFinish);

        Allure.step("Проверить текст благодарности", () -> {
            String message = checkoutCompletePage.getCompleteMessage();
            assertTrue(message.contains("Your order has been dispatched") || message.contains("dispatched"),
                    "Текст благодарности не соответствует ожидаемому");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Возврат на главную страницу")
    @DisplayName("Проверка перехода на главную по кнопке 'Back Home'")
    public void shouldReturnToHomePage() {
        Allure.step("Открыть главную страницу", loginPage::openLoginPage);
        logger.info("Открыта страница логина");
        Allure.step("Авторизоваться стандартным пользователем", () -> {
            loginPage.enterUsername("standard_user")
                    .enterPassword("secret_sauce")
                    .clickLogin();
            logger.info("Авторизация прошла успешно");
        });

        String itemName = "Sauce Labs Backpack";

        Allure.step("Добавить товар в корзину: " + itemName, () -> {
            homePage.addItemToCart(itemName);
            logger.info("Добавлен товар: {}", itemName);
        });

        Allure.step("Перейти в корзину и к оформлению", () -> {
            homePage.clickCart();
            cardPage.clickCheckout();
            checkoutPage.enterFirstName("Иван")
                    .enterLastName("Иванов")
                    .enterCode("123456")
                    .clickContinue();
        });

        Allure.step("Нажать кнопку Finish", overviewPage::clickFinish);

        Allure.step("Нажать кнопку Back Home", checkoutCompletePage::clickBackHome);

        Allure.step("Проверить, что произошел переход на главную страницу", () -> {
            assertTrue(homePage.isOnInventoryPage(),
                    "Переход на главную страницу после завершения заказа не выполнен");
        });
    }
}
